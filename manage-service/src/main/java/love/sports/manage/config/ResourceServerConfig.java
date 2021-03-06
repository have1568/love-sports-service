package love.sports.manage.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

@Configuration
@EnableResourceServer
class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Value("${spring.application.name}")
    private String resourceName;

    private final OAuth2AuthenticationManagerImpl authenticationManagerImpl;

    public ResourceServerConfig(OAuth2AuthenticationManagerImpl authenticationManagerImpl) {
        this.authenticationManagerImpl = authenticationManagerImpl;
    }

    //只拦截API相关接口 EnableResourceServer 先于 EnableWebSecurity 会导致 拦截或者不拦截的接口失效
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.antMatcher("/api/**").authorizeRequests().anyRequest().authenticated();
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.authenticationManager(authenticationManagerImpl);
        resources.resourceId(resourceName);
    }
}
