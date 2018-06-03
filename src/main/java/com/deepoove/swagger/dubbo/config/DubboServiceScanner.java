package com.deepoove.swagger.dubbo.config;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.alibaba.dubbo.rpc.Exporter;
import com.alibaba.dubbo.rpc.protocol.dubbo.DubboProtocol;
import org.springframework.stereotype.Component;

import com.deepoove.swagger.dubbo.http.ReferenceManager;

import io.swagger.config.Scanner;

@Component
public class DubboServiceScanner implements Scanner {

	@Override
	public Set<Class<?>> classes() {
		return interfaceMapRef().keySet();
	}
	
	public Map<Class<?>, Object> interfaceMapRef() {
		return ReferenceManager.getInstance().getInterfaceMapRef();

//		Map<Class<?>, Object> map = new ConcurrentHashMap();
//		Collection<Exporter<?>> collection = DubboProtocol.getDubboProtocol().getExporters();
//		for (Exporter<?> exporter : DubboProtocol.getDubboProtocol().getExporters()) {
//			map.put(exporter.getInvoker().getInterface(), exporter.getInvoker().getInterface());
//		}
//		return map;
	}

	@Override
	public boolean getPrettyPrint() {
		return false;
	}

	@Override
	public void setPrettyPrint(boolean shouldPrettyPrint) {
	}

}
