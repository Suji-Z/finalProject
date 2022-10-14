package com.project.tour.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class PackageMvcConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        exposeDirectory("package-preview", registry);
        exposeDirectory1("package-detail",registry);

    }



    private void exposeDirectory(String dirName1, ResourceHandlerRegistry registry) {
        Path uploadDir1 = Paths.get(dirName1);
        String uploadPath = uploadDir1.toFile().getAbsolutePath();

        if (dirName1.startsWith("../")) dirName1 = dirName1.replace("../", "");

        registry.addResourceHandler("/" + dirName1 + "/**").addResourceLocations("file:/"+ uploadPath + "/");

    }

    private void exposeDirectory1(String dirName2, ResourceHandlerRegistry registry) {
        Path uploadDir2 = Paths.get(dirName2);
        String uploadPath = uploadDir2.toFile().getAbsolutePath();

        if (dirName2.startsWith("../")) dirName2 = dirName2.replace("../", "");

        registry.addResourceHandler("/" + dirName2 + "/**").addResourceLocations("file:/"+ uploadPath + "/");

    }


}
