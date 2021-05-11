package com.youming.demoshiro.shiro.authc.pam;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.realm.Realm;


/**
 * 当有多个Realm凭据时，自定义使用哪个凭证
 * 参考：http://shiro.apache.org/authentication.html#Authentication-%7B%7BAuthenticationStrategy%7D%7D
 */
public class CustomizedAuthenticationStrategy extends ModularRealmAuthenticator {

	@Override
	protected AuthenticationInfo doAuthenticate(AuthenticationToken authenticationToken)
			throws AuthenticationException {
		// 判断getRealms()是否返回为空
		this.assertRealmsConfigured();
		
		//强制转换回自定义的CustomizedToken
        //CustomUsernamePasswordToken customizedToken = (CustomUsernamePasswordToken) authenticationToken;
		String loginType = "student";		//可以通过自定义的token传入类型
		Collection<Realm> realms = this.getRealms();
		Collection<Realm> typeRealms = new ArrayList<>();
		for (Realm realm : realms) {
        	//System.out.println(realm.getName().replace("com.youming.teachertrainingsystem.shiro.realm.", ""));
        	String realmName = realm.getName();
            if (realmName.replace("com.youming.teachertrainingsystem.shiro.realm.", "").contains(loginType)){
            	typeRealms.add(realm);		//根据类型使用特定realm进行验证
            }
                
        }
		
		if (typeRealms.size() == 1) {
            return doSingleRealmAuthentication(typeRealms.iterator().next(), authenticationToken);	//单realm
        } else {
            return doMultiRealmAuthentication(typeRealms, authenticationToken);		//多realm
        }
	}
}
