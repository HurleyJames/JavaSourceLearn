package ac.hurley.springboot.adapter;

import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

public class MyWebMvcConfigureAdapter extends WebMvcConfigurerAdapter {

    /**
     * 配置静态访问资源
     * 添加映射路径
     * addResourceHandlers指的是对外暴露的访问路径
     * addResourceLocations指的是文件放置的目录
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/my/**").addResourceLocations("classpath:/my/");
        super.addResourceHandlers(registry);
    }

    /**
     * 这里配置后，直接访问/toLogin就能跳转到login.jsp页面
     *
     * @param registry
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("toLogin").setViewName("login");
        super.addViewControllers(registry);
    }
}