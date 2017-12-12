package com.aptech.itblog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

//@Configuration
//public class OAuth2ServerConfiguration {
//
//    private static final String RESOURCE_ID = "restservice";
//
////    @Configuration
////    @EnableResourceServer
////    protected static class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {
////
////        @Override
////        public void configure(ResourceServerSecurityConfigurer resources) {
////            resources
////                    .resourceId(RESOURCE_ID);
////        }
////
////        @Override
////        public void configure(HttpSecurity http) throws Exception {
////            http
////                    .authorizeRequests()
////                    .antMatchers("/secure/**").authenticated()
////                    .antMatchers("/debug").authenticated()
////                    .antMatchers("/users/**").authenticated()
////                    .antMatchers("/admin").hasRole("ADMIN")
////                    .antMatchers("/greeting").authenticated();
////        }
////
////    }
//
//    @Configuration
//    @EnableResourceServer
//    protected static class ResourceServerConfiguration extends
//            ResourceServerConfigurerAdapter {
//
//        @Override
//        public void configure(ResourceServerSecurityConfigurer resources) {
//            resources
//                    .resourceId(RESOURCE_ID);
//        }
//
//        @Override
//        public void configure(HttpSecurity http) throws Exception {
//            http
//                    .authorizeRequests()
//                    .antMatchers("/hello").authenticated()
//                    .antMatchers("/users").hasRole("ADMIN")
//                    .antMatchers("/greeting").authenticated();
//        }
//
//    }
//
//    @Configuration
//    @EnableAuthorizationServer
//    public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {
//        ///////////////////
//        // Use In Memory //
//        ///////////////////
////        @Autowired
////        private ApplicationConfigurationProperties appConfig;
////
////        @Autowired
////        @Qualifier("userDetailsService")
////        private UserDetailsService userDetailsService;
////
////        @Autowired
////        private AuthenticationManager authenticationManager;
////
////        @Bean
////        public PasswordEncoder passwordEncoder() {
////            return new BCryptPasswordEncoder();
////        }
////
////        @Override
////        public void configure(AuthorizationServerEndpointsConfigurer configurer) throws Exception {
////            configurer.authenticationManager(authenticationManager);
////            configurer.userDetailsService(userDetailsService);
////        }
////
////        @Override
////        public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
////            String clientId = appConfig.getClientId();
////            String clientSecret = appConfig.getClientSecret();
////            clients
////                    .inMemory()
////                    .withClient(clientId)
////                    .secret(clientSecret)
////                    .accessTokenValiditySeconds(360)
////                    .refreshTokenValiditySeconds(720)
////                    .scopes("read", "write")
////                    .authorizedGrantTypes("password", "refresh_token")
////                    .resourceIds(RESOURCE_ID);
////        }
//
//
//        private TokenStore tokenStore = new InMemoryTokenStore();
//
//        @Autowired
//        @Qualifier("authenticationManagerBean")
//        private AuthenticationManager authenticationManager;
//
//        @Autowired
//        @Qualifier("userDetailsService")
//        private UserDetailsService userDetailsService;
//
//        @Override
//        public void configure(AuthorizationServerEndpointsConfigurer endpoints)
//                throws Exception {
//            endpoints
//                    .tokenStore(this.tokenStore)
//                    .authenticationManager(this.authenticationManager)
//                    .userDetailsService(userDetailsService);
//        }
//
//        @Override
//        public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//            clients
//                    .inMemory()
//                    .withClient("clientapp")
//                    .authorizedGrantTypes("password", "refresh_token")
//                    .authorities("USER")
//                    .scopes("read", "write")
//                    .resourceIds(RESOURCE_ID)
//                    .secret("123456")
//                    .accessTokenValiditySeconds(360)
//                    .refreshTokenValiditySeconds(720);
//        }
//
//        @Bean
//        @Primary
//        public DefaultTokenServices tokenServices() {
//            DefaultTokenServices tokenServices = new DefaultTokenServices();
//            tokenServices.setSupportRefreshToken(true);
//            tokenServices.setTokenStore(this.tokenStore);
//            return tokenServices;
//        }
//
//
//        //////////////
//        // Use JDBC //
//        //////////////
////        @Autowired
////        public DataSource dataSource;
////
////        @Bean
////        public JdbcTokenStore tokenStore() {
////            return new JdbcTokenStore(dataSource);
////        }
////
////        @Bean
////        public AuthorizationCodeServices authorizationCodeServices() {
////            return new JdbcAuthorizationCodeServices(dataSource);
////        }
////
////        @Autowired
////        @Qualifier("authenticationManagerBean")
////        public AuthenticationManager authenticationManager;
////
////        @Autowired
////        @Qualifier("userDetailsService")
////        private UserDetailsService userDetailsService;
////
////        @Override
////        public void configure(AuthorizationServerEndpointsConfigurer endpoints)
////                throws Exception {
////            endpoints.userDetailsService(userDetailsService)
////                    .authorizationCodeServices(authorizationCodeServices())
////                    .authenticationManager(this.authenticationManager)
////                    .tokenStore(tokenStore())
////                    .approvalStoreDisabled();
////        }
////
////
////        @Override
////        public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
////            clients
////                    .jdbc(dataSource)
////                    .passwordEncoder(passwordEncoder());
//////                    .withClient("clientapp")
//////                    .authorizedGrantTypes("password", "refresh_token")
//////                    .authorities("USER")
//////                    .scopes("read", "write")
//////                    .resourceIds(RESOURCE_ID)
//////                    .secret("123456")
//////                    .accessTokenValiditySeconds(360)
//////                    .refreshTokenValiditySeconds(720);
////        }
////
////        // https://github.com/spring-projects/spring-security-oauth/tree/master/tests/annotation/jdbc/src/main/java/demo
////        @Override
////        public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
////            security
////                    .passwordEncoder(passwordEncoder());
////        }
////
////        @Bean
////        public PasswordEncoder passwordEncoder() {
////            return new BCryptPasswordEncoder();
////        }
//
//    }
//}


@Configuration
public class OAuth2ServerConfiguration {

    private static final String RESOURCE_ID = "restservice";

    @Configuration
    @EnableResourceServer
    protected static class ResourceServerConfiguration extends
            ResourceServerConfigurerAdapter {

        @Override
        public void configure(ResourceServerSecurityConfigurer resources) {
            resources
                    .resourceId(RESOURCE_ID);
        }

        @Override
        public void configure(HttpSecurity http) throws Exception {
            http
                    .authorizeRequests()
                    .antMatchers("/hello").authenticated()
                    .antMatchers("/users").hasRole("ADMIN")
                    .antMatchers("/greeting").authenticated();
        }

    }

    @Configuration
    @EnableAuthorizationServer
    protected static class AuthorizationServerConfiguration extends
            AuthorizationServerConfigurerAdapter {

//        private TokenStore tokenStore = new InMemoryTokenStore();
//
//        @Autowired
//        @Qualifier("authenticationManagerBean")
//        private AuthenticationManager authenticationManager;
//
//        @Autowired
//        @Qualifier("userDetailsService")
//        private UserDetailsService userDetailsService;
//
//        @Override
//        public void configure(AuthorizationServerEndpointsConfigurer endpoints)
//                throws Exception {
//            endpoints
//                    .tokenStore(this.tokenStore)
//                    .authenticationManager(this.authenticationManager)
//                    .userDetailsService(userDetailsService);
//        }
//
//        @Override
//        public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//            clients
//                    .inMemory()
//                    .withClient("clientapp")
//                    .authorizedGrantTypes("password", "refresh_token")
//                    .authorities("USER")
//                    .scopes("read", "write")
//                    .resourceIds(RESOURCE_ID)
//                    .secret("123456")
//                    .accessTokenValiditySeconds(360)
//                    .refreshTokenValiditySeconds(720);
//        }
//
//        @Bean
//        @Primary
//        public DefaultTokenServices tokenServices() {
//            DefaultTokenServices tokenServices = new DefaultTokenServices();
//            tokenServices.setSupportRefreshToken(true);
//            tokenServices.setTokenStore(this.tokenStore);
//            return tokenServices;
//        }


        ///////////////////
        // Use In Memory //
        ///////////////////
        @Autowired
        private ApplicationConfigurationProperties appConfig;

        @Autowired
        @Qualifier("userDetailsService")
        private UserDetailsService userDetailsService;

        @Autowired
        private AuthenticationManager authenticationManager;

        @Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }

        @Override
        public void configure(AuthorizationServerEndpointsConfigurer configurer) throws Exception {
            configurer.authenticationManager(authenticationManager);
            configurer.userDetailsService(userDetailsService);
        }

        @Override
        public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
            String clientId = appConfig.getClientId();
            String clientSecret = appConfig.getClientSecret();
            clients
                    .inMemory()
                    .withClient(clientId)
                    .secret(clientSecret)
                    .accessTokenValiditySeconds(360)
                    .refreshTokenValiditySeconds(720)
                    .scopes("read", "write")
                    .authorizedGrantTypes("password", "refresh_token")
                    .resourceIds(RESOURCE_ID);
        }


    }

}
