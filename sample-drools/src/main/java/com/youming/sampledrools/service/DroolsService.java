package com.youming.sampledrools.service;

import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieModule;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.io.ResourceFactory;

import com.google.protobuf.Extension.MessageType;
import com.youming.sampledrools.domain.Message;

/**
 * Drools的服务样例
 * */
public class DroolsService {
	
	public static void hello() {
		KieServices kieServices = KieServices.Factory.get();
		
		KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
		kieFileSystem.write(ResourceFactory.newClassPathResource("drools/hello.drl"));		//注入规则文件
		KieBuilder kieBuilder = kieServices.newKieBuilder(kieFileSystem);
		kieBuilder.buildAll();
        KieModule kieModule = kieBuilder.getKieModule();

        KieContainer kContainer = kieServices.newKieContainer(kieModule.getReleaseId());
        KieSession  kieSession = kContainer.newKieSession();		//编译规则，如果通过则生成会话
        
        Message message1 = new Message(Message.MessageType.HI, "杨过");
        kieSession.insert(message1);		//插入变化的数据
        kieSession.fireAllRules();
        
        Message message2 = new Message(Message.MessageType.GOODBYE, "杨过");
        kieSession.insert(message2);
        kieSession.fireAllRules();
      
        kieSession.dispose();		//资源释放
	}
}
