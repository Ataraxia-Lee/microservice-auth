package com.ataraxia.microservices.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

import javax.annotation.Resource;

/**
 * @author MyPC
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServer extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Resource
    ClientDetailsService clientDetailsService;

    @Bean
    TokenStore tokenStore() {
        return new InMemoryTokenStore();
    }

    /**
     * 令牌管理
     */
    @Bean
    AuthorizationServerTokenServices tokenServices() {
        DefaultTokenServices services = new DefaultTokenServices();
        //客户端详情服务
        services.setClientDetailsService(clientDetailsService);
        //支持令牌刷新
        services.setSupportRefreshToken(true);
        //令牌存储策略
        services.setTokenStore(tokenStore());
        //令牌默认有效期2小时
        services.setAccessTokenValiditySeconds(60 * 60 * 2);
        //刷新令牌默认有效期2天
        services.setRefreshTokenValiditySeconds(60 * 60 * 24 * 3);
        return services;
    }


    /**
     * 访问安全配置
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security
                //资源服务器访问公钥端点[/oauth/token_key]
                //.tokenKeyAccess("permitAll()")
                //检查令牌访问 [/oauth/check_token]
                .checkTokenAccess("permitAll()")
                //允许客户进行表单身份验证
                .allowFormAuthenticationForClients();
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("dimples")
                .secret(new BCryptPasswordEncoder().encode("123456"))
                // 为了测试，所以开启所有的方式，实际业务根据需要选择
                .authorizedGrantTypes("authorization_code", "password", "client_credentials", "implicit", "refresh_token")
                .accessTokenValiditySeconds(3600)
                .refreshTokenValiditySeconds(864000)
                .scopes("select")
                // false跳转到授权页面，在授权码模式中会使用到
                .autoApprove(false)
                // 验证回调地址
                .redirectUris("http://www.baidu.com");
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                //密码模式下验证信息
                .authenticationManager(authenticationManager)
                .tokenServices(tokenServices());
    }
}
