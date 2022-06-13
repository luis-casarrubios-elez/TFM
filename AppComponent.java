/*
 * Copyright 2022-present Open Networking Foundation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.foo;

import org.onosproject.cfg.ComponentConfigService;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.onlab.packet.Ethernet;
import org.onlab.packet.IPv4;
import org.onlab.packet.TCP;
import org.onosproject.core.CoreService;
import org.onosproject.net.packet.PacketContext;
import org.onosproject.net.packet.OutboundPacket;
import org.onosproject.net.packet.InboundPacket;
import org.onosproject.net.packet.DefaultOutboundPacket;
import org.onosproject.net.packet.PacketPriority;
import org.onosproject.net.packet.PacketProcessor;
import org.onosproject.net.packet.PacketService;
import org.onlab.packet.MacAddress;
import org.onlab.packet.IpAddress;
import org.onosproject.net.flow.DefaultTrafficTreatment;
import org.onosproject.net.flow.TrafficTreatment;
import org.onosproject.net.DeviceId;
import java.nio.ByteBuffer;
import org.onlab.packet.IPacket;
import org.onlab.packet.Data;
import org.foo.*;
import java.io.*;
import static org.onlab.packet.PacketUtils.*;
import org.onlab.packet.BasePacket;
import java.nio.*;
import java.util.*;
import org.onosproject.net.ConnectPoint;

import java.util.Dictionary;
import java.util.Properties;

import static org.onlab.util.Tools.get;

import org.onosproject.core.ApplicationId;
import org.onosproject.net.flow.TrafficSelector;
import org.onosproject.net.packet.PacketPriority;
import org.onlab.packet.TpPort;
import org.onosproject.net.flow.TrafficTreatment;
import org.onosproject.net.HostId;
import org.onosproject.net.flowobjective.FlowObjectiveService;
import org.onosproject.net.flow.FlowRuleService;
import org.onosproject.net.Host;
import org.onosproject.net.flow.DefaultTrafficSelector;
import org.onosproject.net.flow.FlowRule;
import org.onosproject.net.flowobjective.DefaultForwardingObjective;
import org.onosproject.net.host.HostService;
import org.onosproject.net.flowobjective.ForwardingObjective;
import org.onosproject.net.packet.PacketPriority;
import org.onosproject.net.PortNumber;
import org.onlab.packet.IpPrefix;

/**
 * Skeletal ONOS application component.
 */
@Component(immediate = true,
        service = {SomeInterface.class},
        property = {
                "someProperty=Some Default String Value",
        })
public class AppComponent implements SomeInterface {

    private final Logger log = LoggerFactory.getLogger(getClass());

    /** Some configurable property. */
    private String someProperty;

    private static final int PRIORITY_INT = 128;

    @Reference(cardinality = ReferenceCardinality.MANDATORY)
    protected ComponentConfigService cfgService;

    @Reference(cardinality = ReferenceCardinality.MANDATORY)
    protected CoreService coreService;

    @Reference(cardinality = ReferenceCardinality.MANDATORY)
    protected PacketService packetService;

    @Reference(cardinality = ReferenceCardinality.MANDATORY)
    protected FlowObjectiveService flowObjectiveService;

    @Reference(cardinality = ReferenceCardinality.MANDATORY)
    protected FlowRuleService flowRuleService;

    @Reference(cardinality = ReferenceCardinality.MANDATORY)
    protected HostService hostService;

    private final PacketProcessor packetProcessor = new TcpPacketProcessor();
    private ApplicationId appId;

    @Activate
    protected void activate() {
        cfgService.registerProperties(getClass());
        appId = coreService.registerApplication("org.foo.app",
                                                () -> log.info("Periscope down."));
        packetService.addProcessor(packetProcessor, PacketPriority.CONTROL.priorityValue());
        requestIntercepts();
        log.info("Started");
    }

    @Deactivate
    protected void deactivate() {
        cfgService.unregisterProperties(getClass(), false);
        withdrawIntercepts();
        flowRuleService.removeFlowRulesById(appId);
        packetService.removeProcessor(packetProcessor);
        log.info("Stopped");
    }

    @Modified
    public void modified(ComponentContext context) {
        Dictionary<?, ?> properties = context != null ? context.getProperties() : new Properties();
        if (context != null) {
            someProperty = get(properties, "someProperty");
        }
        log.info("Reconfigured");
    }

    @Override
    public void someMethod() {
        log.info("Invoked");
    }

    /**
     * Request packet in via packet service
     * Here, the app install the rules in the SDN switches to redirect certain traffic to the controller, called when activating
     */
    private void requestIntercepts() {
        
        // Set of IP addresses/prefixes and TCP ports needed
        IpAddress cacheIp = IpAddress.valueOf("10.10.1.3"); // I use the IPs to compute the prefixes
        IpAddress originIp = IpAddress.valueOf("10.10.2.1");
        IpAddress clientIp = IpAddress.valueOf("10.10.0.1");
        
        IpPrefix prefixOrigin = IpPrefix.valueOf(originIp, 32); // 10.10.2.1/32 (only that address)
        IpPrefix prefixCache = IpPrefix.valueOf(cacheIp, 32); // 10.10.1.3/32 (only that address)
        IpPrefix prefixClient = IpPrefix.valueOf(clientIp, 32); // 10.10.0.1/32 (only that address)

        TpPort httpPort = TpPort.tpPort(80);
        TpPort atsPort = TpPort.tpPort(8080);

        // One rule for HTTP requests to origin server on port 80 from the client
        TrafficSelector.Builder selector = DefaultTrafficSelector.builder();
        selector.matchEthType(Ethernet.TYPE_IPV4)
                .matchIPSrc(prefixClient)
                .matchIPDst(prefixOrigin)
                .matchTcpDst(httpPort)
                .matchIPProtocol(IPv4.PROTOCOL_TCP);
        packetService.requestPackets(selector.build(), PacketPriority.CONTROL, appId);

        // One rule for Proxy responses coming from port 8080 (always to the client, no filter needed)
        TrafficSelector.Builder selector2 = DefaultTrafficSelector.builder();
        selector2.matchEthType(Ethernet.TYPE_IPV4)
                .matchIPSrc(prefixCache)
                .matchTcpSrc(atsPort)
                .matchIPProtocol(IPv4.PROTOCOL_TCP);
        packetService.requestPackets(selector2.build(), PacketPriority.CONTROL, appId);

    }

    /**
     * Cancel request for packet in via packet service
     * Here, the app uninstall the rules from the SDN switches, called when deactivating
     */
    private void withdrawIntercepts() {
        
        // Set of IP addresses/prefixes and TCP ports needed
        TpPort httpPort = TpPort.tpPort(80);
        TpPort atsPort = TpPort.tpPort(8080);
        
        IpAddress cacheIp = IpAddress.valueOf("10.10.1.3"); // I use the IPs to compute the prefixes
        IpAddress originIp = IpAddress.valueOf("10.10.2.1");
        IpAddress clientIp = IpAddress.valueOf("10.10.0.1");
        
        IpPrefix prefixOrigin = IpPrefix.valueOf(originIp, 32); // 10.10.2.1/32 (only that address)
        IpPrefix prefixCache = IpPrefix.valueOf(cacheIp, 32); // 10.10.1.3/32 (only that address)
        IpPrefix prefixClient = IpPrefix.valueOf(clientIp, 32); // 10.10.0.1/32 (only that address)
        
        // Cancel the previously installed rules
        TrafficSelector.Builder selector = DefaultTrafficSelector.builder();
        selector.matchEthType(Ethernet.TYPE_IPV4)
                .matchIPSrc(prefixClient)
                .matchIPDst(prefixOrigin)
                .matchTcpDst(httpPort)
                .matchIPProtocol(IPv4.PROTOCOL_TCP);
        packetService.cancelPackets(selector.build(), PacketPriority.CONTROL, appId);

        TrafficSelector.Builder selector2 = DefaultTrafficSelector.builder();
        selector2.matchEthType(Ethernet.TYPE_IPV4)
                .matchIPSrc(prefixCache)
                .matchTcpSrc(atsPort)
                .matchIPProtocol(IPv4.PROTOCOL_TCP);
        packetService.cancelPackets(selector2.build(), PacketPriority.CONTROL, appId);

    }

    // Intercepts packets
    // Decide if packets coming to Controller must be processed
    private class TcpPacketProcessor implements PacketProcessor {
        @Override
        public void process(PacketContext context) {
            ConnectPoint srcPoint = context.inPacket().receivedFrom();
            Ethernet eth = context.inPacket().parsed();
            if (isTcpHttpRequest(eth)) {
                processTcpHttpRequest(context, eth, srcPoint);
                log.info("HTTP Request Intercepted!");
            }
            if (isTcpHttpResponse(eth)){
                processTcpHttpResponse(context, eth, srcPoint);
                log.info("HTTP Response Intercepted!");
            }
        }
    }

    // Indicates whether the specified packet corresponds to TCP with destination port 80 aka HTTP Request
    // Only if coming from the client to the Origin server
    // So that we don't mess the other HTTP traffic
    private boolean isTcpHttpRequest(Ethernet eth) {
        if (eth.getEtherType() == Ethernet.TYPE_IPV4) {
            IPv4 ipPacket = (IPv4) eth.getPayload();
            // 10.10.2.1 == 0xA0A0201 == 168428033
            if (ipPacket.getProtocol() == IPv4.PROTOCOL_TCP
                    && ipPacket.getDestinationAddress() == 168428033
                    && ipPacket.getSourceAddress() == 168427521){
                TCP tcpPacket = (TCP) ipPacket.getPayload();
                return tcpPacket.getDestinationPort() == 80;
            }
        }
        return false;
    }

    // Indicates whether the specified packet corresponds to TCP with source port 8080 aka Proxy Response
    // Only if coming from the Proxy server
    private boolean isTcpHttpResponse(Ethernet eth) {
        if (eth.getEtherType() == Ethernet.TYPE_IPV4) {
            IPv4 ipPacket = (IPv4) eth.getPayload();
            // 10.10.1.3 == 0xA0A0103 == 168427779
            if (ipPacket.getProtocol() == IPv4.PROTOCOL_TCP
                    && ipPacket.getSourceAddress() == 168427779){
                TCP tcpPacket = (TCP) ipPacket.getPayload();
                return tcpPacket.getSourcePort() == 8080;
            }
        }
        return false;
    }

    // Install the rule for the requests way of the communication
    // Processes the specified TCP HTTP packet to be affected by this rule too
    private void processTcpHttpRequest(PacketContext context, Ethernet eth, ConnectPoint srcPoint) {

        // Parses the Ethernet packet to extract the IP and TCP payloads
        IPv4 ipPacket = (IPv4) eth.getPayload();
        TCP tcpPacket = (TCP) ipPacket.getPayload();

        // Set of MACs/IPs/Ports needed
        MacAddress cacheMac = MacAddress.valueOf("02:fd:00:00:02:01");
        
        IpAddress cacheIp = IpAddress.valueOf("10.10.1.3");
        IpAddress originIp = IpAddress.valueOf("10.10.2.1");
        IpAddress clientIp = IpAddress.valueOf("10.10.0.1");
        
        TpPort dstPort = TpPort.tpPort(tcpPacket.getDestinationPort());
        TpPort atsPort = TpPort.tpPort(8080);

        IpPrefix prefix = IpPrefix.valueOf(originIp, 32); // For VNX intermediate scenario
        IpPrefix prefixClient = IpPrefix.valueOf(clientIp, 32);

        // May throw Null Pointer exception if ONOS ignore the host (pingall at the beginning to avoid it)
        HostId hid = HostId.hostId(cacheMac);
        Host cache = hostService.getHost(hid);

        // Defines the match part of the OpenFlow rule to install
        // "IP packets from the client to the Origin server with TCP destination port 80"
        TrafficSelector selector = DefaultTrafficSelector.builder()
                .matchEthType(Ethernet.TYPE_IPV4)
                .matchIPSrc(prefixClient)
                .matchIPDst(prefix)
                .matchTcpDst(dstPort)
                .matchIPProtocol(IPv4.PROTOCOL_TCP)
                .build();

        // Defines the action part of the OpenFlow rule to install
        // "Change the Ethernet and IP destination addresses and TCP destination port"
        TrafficTreatment treatment = DefaultTrafficTreatment.builder()
                .setEthDst(cacheMac)
                .setIpDst(cacheIp)
                .setTcpDst(atsPort)
                // This sends the context packet to the OpenFlow pipeline again to let fwd app to forward it correctly
                .setOutput(PortNumber.TABLE)
                .build();

        // Install the new flow rule in the switch where the packet was received from
        flowObjectiveService.forward(context.inPacket().receivedFrom().deviceId(), DefaultForwardingObjective.builder()
                .fromApp(appId)
                .withSelector(selector)
                .withTreatment(treatment)
                .withFlag(ForwardingObjective.Flag.VERSATILE)
                .makePermanent()
                .withPriority(FlowRule.MAX_PRIORITY)
                .add());

        // The current packet is also treated
        context.treatmentBuilder()
                .setEthDst(cacheMac)
                .setIpDst(cacheIp)
                .setTcpDst(atsPort)
                .setOutput(PortNumber.TABLE); // This sends the context packet to the OpenFlow pipeline again to be correctly treated
        context.send();
    }

    // Install the rule for the responses way of the communication
    // Processes the specified TCP HTTP packet to be affected by this rule too
    private void processTcpHttpResponse(PacketContext context, Ethernet eth, ConnectPoint srcPoint) {

        // Parses the Ethernet packet to extract the IP and TCP payloads
        IPv4 ipPacket = (IPv4) eth.getPayload();
        TCP tcpPacket = (TCP) ipPacket.getPayload();

        // Set of MACs/IPs/Ports needed
        MacAddress originMac = MacAddress.valueOf("02:fd:00:00:03:01"); // For VNX intermediate scenario
        MacAddress r1Mac = MacAddress.valueOf("02:fd:00:00:00:02"); // For VNX intermediate scenario

        IpAddress originIp = IpAddress.valueOf("10.10.2.1"); // For VNX intermediate scenario
        IpAddress cacheIp = IpAddress.valueOf("10.10.1.3"); // For VNX intermediate scenario
        
        TpPort srcPort = TpPort.tpPort(tcpPacket.getSourcePort());
        TpPort httpPort = TpPort.tpPort(80);

        IpPrefix prefix = IpPrefix.valueOf(cacheIp, 32); // For VNX intermediate scenario

        // May throw Null Pointer exception if ONOS ignore the host (pingall at the beginning to avoid it)
        HostId hid = HostId.hostId(originMac);
        Host origin = hostService.getHost(hid);
        HostId r1id = HostId.hostId(r1Mac);
        Host r1 = hostService.getHost(r1id);

        // Defines the match part of the OpenFlow rule to install
        // "IP packets from the Proxy with TCP source port 8080"
        TrafficSelector selector = DefaultTrafficSelector.builder()
                .matchEthType(Ethernet.TYPE_IPV4)
                .matchIPSrc(prefix)
                .matchTcpSrc(srcPort)
                .matchIPProtocol(IPv4.PROTOCOL_TCP)
                .build();

        // Defines the action part of the OpenFlow rule to install
        // "Change the IP source address and TCP source port"
        TrafficTreatment treatment = DefaultTrafficTreatment.builder()
                //Notice there is no need to modify the SrcMac
                .setIpSrc(originIp)
                .setTcpSrc(httpPort)
                // This sends the context packet to the OpenFlow pipeline again to let fwd app to forward it correctly
                .setOutput(PortNumber.TABLE)
                .build();

        // Install the new flow rule in the switch where the packet was received from
        flowObjectiveService.forward(context.inPacket().receivedFrom().deviceId(), DefaultForwardingObjective.builder()
                .fromApp(appId)
                .withSelector(selector)
                .withTreatment(treatment)
                .withFlag(ForwardingObjective.Flag.VERSATILE)
                .makePermanent()
                .withPriority(FlowRule.MAX_PRIORITY - 1 )
                .add());

        // The current packet is also treated
        context.treatmentBuilder()
                //Notice there is no need to modify the SrcMac
                .setIpSrc(originIp)
                .setTcpSrc(httpPort)
                .setOutput(PortNumber.TABLE); // This sends the context packet to the OpenFlow pipeline again to be correctly treated
        context.send();
    }
}