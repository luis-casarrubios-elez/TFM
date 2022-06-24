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
package org.tfm;

import org.onlab.packet.IpAddress;
import org.onlab.packet.MacAddress;
import org.onlab.packet.IpPrefix;
import org.onlab.packet.TpPort;
import org.onosproject.core.ApplicationId;
import org.onosproject.net.config.Config;
import org.onosproject.net.config.basics.BasicElementConfig;

import static org.onosproject.net.config.Config.FieldPresence.MANDATORY;
import static org.onosproject.net.config.Config.FieldPresence.OPTIONAL;

/**
 * App Config class.
 */
public class IcnConfig extends Config<ApplicationId> {

    public static final String CACHE_MAC1 = "cachemac1";
    public static final String CACHE_MAC2 = "cachemac2";
    public static final String ORIGIN_MAC = "originmac";
    public static final String CLIENT_IP1 = "clientip1";
    public static final String CLIENT_IP2 = "clientip2";
    public static final String CACHE_IP1 = "cacheip1";
    public static final String CACHE_IP2 = "cacheip2";
    public static final String ORIGIN_IP = "originip";
    public static final String PREFIX_LEN = "prefixlen";
    public static final String ATS_PORT = "atsport";
    public static final String HTTP_PORT = "httpport";

    /**
     * Returns the cache 1 mac.
     *
     * @return cache 1 mac or null if not set
     */
    public MacAddress cacheMac1() {
        String cacheMac1 = get(CACHE_MAC1, null);
        return cacheMac1 != null ? MacAddress.valueOf(cacheMac1) : null;
    }

    /**
     * Sets the cache 1 mac.
     *
     * @param mac new mac address; null to clear
     * @return self
     */
    public BasicElementConfig cacheMac1(String cacheMac1) {
        return (BasicElementConfig) setOrClear(CACHE_MAC1, cacheMac1);
    }

    /**
     * Returns the cache 2 mac.
     *
     * @return cache 2 mac or null if not set
     */
    public MacAddress cacheMac2() {
        String cacheMac2 = get(CACHE_MAC2, null);
        return cacheMac2 != null ? MacAddress.valueOf(cacheMac2) : null;
    }

    /**
     * Sets the cache 2 mac.
     *
     * @param mac new mac address; null to clear
     * @return self
     */
    public BasicElementConfig cacheMac2(String cacheMac2) {
        return (BasicElementConfig) setOrClear(CACHE_MAC2, cacheMac2);
    }

    /**
     * Returns the origin mac.
     *
     * @return origin server mac or null if not set
     */
    public MacAddress originMac() {
        String originMac = get(ORIGIN_MAC, null);
        return originMac != null ? MacAddress.valueOf(originMac) : null;
    }

    /**
     * Sets the origin server mac.
     *
     * @param mac new mac address; null to clear
     * @return self
     */
    public BasicElementConfig originMac(String originMac) {
        return (BasicElementConfig) setOrClear(ORIGIN_MAC, originMac);
    }

    /**
     * Returns the client 1 ip.
     *
     * @return ip address or null if not set
     */
    public IpAddress clientIp1() {
        String clientIp1 = get(CLIENT_IP1, null);
        return clientIp1 != null ? IpAddress.valueOf(clientIp1) : null;
    }

    /**
     * Sets the client 1 ip.
     *
     * @param ip new ip address; null to clear
     * @return self
     */
    public BasicElementConfig clientIp1(String clientIp1) {
        return (BasicElementConfig) setOrClear(CLIENT_IP1, clientIp1);
    }

    /**
     * Returns the client 2 ip.
     *
     * @return ip address or null if not set
     */
    public IpAddress clientIp2() {
        String clientIp2 = get(CLIENT_IP2, null);
        return clientIp2 != null ? IpAddress.valueOf(clientIp2) : null;
    }

    /**
     * Sets the client 2 ip.
     *
     * @param ip new ip address; null to clear
     * @return self
     */
    public BasicElementConfig clientIp2(String clientIp2) {
        return (BasicElementConfig) setOrClear(CLIENT_IP2, clientIp2);
    }

    /**
     * Returns the cache 1 server ip.
     *
     * @return ip address or null if not set
     */
    public IpAddress cacheIp1() {
        String cacheIp1 = get(CACHE_IP1, null);
        return cacheIp1 != null ? IpAddress.valueOf(cacheIp1) : null;
    }

    /**
     * Sets the cache 1 server ip.
     *
     * @param ip new ip address; null to clear
     * @return self
     */
    public BasicElementConfig cacheIp1(String cacheIp1) {
        return (BasicElementConfig) setOrClear(CACHE_IP1, cacheIp1);
    }

    /**
     * Returns the cache 2 server ip.
     *
     * @return ip address or null if not set
     */
    public IpAddress cacheIp2() {
        String cacheIp2 = get(CACHE_IP2, null);
        return cacheIp2 != null ? IpAddress.valueOf(cacheIp2) : null;
    }

    /**
     * Sets the cache 2 server ip.
     *
     * @param ip new ip address; null to clear
     * @return self
     */
    public BasicElementConfig cacheIp2(String cacheIp2) {
        return (BasicElementConfig) setOrClear(CACHE_IP2, cacheIp2);
    }

    /**
     * Returns the origin server ip.
     *
     * @return ip address or null if not set
     */
    public IpAddress originIp() {
        String originIp = get(ORIGIN_IP, null);
        return originIp != null ? IpAddress.valueOf(originIp) : null;
    }

    /**
     * Sets the origin server ip.
     *
     * @param ip new ip address; null to clear
     * @return self
     */
    public BasicElementConfig originIp(String originIp) {
        return (BasicElementConfig) setOrClear(ORIGIN_IP, originIp);
    }

    /**
     * Returns the ip prefixes length.
     *
     * @return prefixes length or -1 if not set
     */
    public int prefixLen() {
        return get(PREFIX_LEN, -1);
    }

    /**
     * Sets the ip prefixes length.
     *
     * @param ip prefixes length new ip prefixes length; null to clear
     * @return self
     */
    public BasicElementConfig prefixLen(int prefixLen) {
        return (BasicElementConfig) setOrClear(PREFIX_LEN, prefixLen);
    }

    /**
     * Returns the Apache Traffic Server listening port.
     *
     * @return ats port or -1 if not set
     */
    public int atsPort() {
        return get(ATS_PORT, -1);
    }

    /**
     * Sets the Apache Traffic Server listening port.
     *
     * @param ats port new ats port; null to clear
     * @return self
     */
    public BasicElementConfig atsPort(int atsPort) {
        return (BasicElementConfig) setOrClear(ATS_PORT, atsPort);
    }

    /**
     * Returns the Apache2 web server listening port.
     *
     * @return http port or -1 if not set
     */
    public int httpPort() {
        return get(HTTP_PORT, -1);
    }

    /**
     * Sets the Apache2 web server listening port.
     *
     * @param http port new http port; null to clear
     * @return self
     */
    public BasicElementConfig httpPort(int httpPort) {
        return (BasicElementConfig) setOrClear(HTTP_PORT, httpPort);
    }
}
