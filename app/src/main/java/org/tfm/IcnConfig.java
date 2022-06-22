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

    public static final String CACHE_MAC = "cachemac";
    public static final String ORIGIN_MAC = "originmac";
    public static final String R1_MAC = "r1mac";
    public static final String CLIENT_IP = "clientip";
    public static final String CACHE_IP = "cacheip";
    public static final String ORIGIN_IP = "originip";
    public static final String PREFIX_LEN = "prefixlen";
    public static final String ATS_PORT = "atsport";
    public static final String HTTP_PORT = "httpport";

    /**
     * Returns the cache mac.
     *
     * @return cache mac or null if not set
     */
    public MacAddress cacheMac() {
        String cacheMac = get(CACHE_MAC, null);
        return cacheMac != null ? MacAddress.valueOf(cacheMac) : null;
    }

    /**
     * Sets the cache mac.
     *
     * @param mac new mac address; null to clear
     * @return self
     */
    public BasicElementConfig cacheMac(String cacheMac) {
        return (BasicElementConfig) setOrClear(CACHE_MAC, cacheMac);
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
     * Returns the r1 mac.
     *
     * @return r1 mac or null if not set
     */
    public MacAddress r1Mac() {
        String r1Mac = get(R1_MAC, null);
        return r1Mac != null ? MacAddress.valueOf(r1Mac) : null;
    }

    /**
     * Sets the r1 mac.
     *
     * @param mac new mac address; null to clear
     * @return self
     */
    public BasicElementConfig r1Mac(String r1Mac) {
        return (BasicElementConfig) setOrClear(R1_MAC, r1Mac);
    }

    /**
     * Returns the client ip.
     *
     * @return ip address or null if not set
     */
    public IpAddress clientIp() {
        String clientIp = get(CLIENT_IP, null);
        return clientIp != null ? IpAddress.valueOf(clientIp) : null;
    }

    /**
     * Sets the client ip.
     *
     * @param ip new ip address; null to clear
     * @return self
     */
    public BasicElementConfig clientIp(String clientIp) {
        return (BasicElementConfig) setOrClear(CLIENT_IP, clientIp);
    }

    /**
     * Returns the cache server ip.
     *
     * @return ip address or null if not set
     */
    public IpAddress cacheIp() {
        String cacheIp = get(CACHE_IP, null);
        return cacheIp != null ? IpAddress.valueOf(cacheIp) : null;
    }

    /**
     * Sets the cache server ip.
     *
     * @param ip new ip address; null to clear
     * @return self
     */
    public BasicElementConfig cacheIp(String cacheIp) {
        return (BasicElementConfig) setOrClear(CACHE_IP, cacheIp);
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
