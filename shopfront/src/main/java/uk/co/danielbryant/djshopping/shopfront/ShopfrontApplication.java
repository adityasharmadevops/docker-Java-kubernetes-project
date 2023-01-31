package uk.co.danielbryant.djshopping.shopfront;

import com.oracle.apm.tracer.ApmTracer;
import io.opentracing.Span;
import io.opentracing.Tracer;
import io.opentracing.tag.Tags;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableHystrix
public class ShopfrontApplication {

    public static void main(String[] args) {
        try{
        Tracer tracer = new ApmTracer.Builder("mytraceA", "APM Tracing Project")
                    .withMicrosecondAccurateTimestamp(true)
                    .withCollectMetrics(true)
                    .withCollectResources(true)
                    .withDataUploadEndpoint("https://aaaadboo7t2z4aaaaaaaaabzjq.apm-agt.us-ashburn-1.oci.oraclecloud.com")
                    .withProperty("com.oracle.apm.agent.private.data.key", "BDEHKOB55OD4HY4RMACS35YFA4J5GSYU")
                    .withProperty("com.oracle.apm.agent.data.upload.file","observations.log")
                    .withProperty("com.oracle.apm.agent.data.upload.proxy.url","http://www-proxy-hqdc.us.oracle.com:80")
                    .build();
            System.out.println("tracer created:" + ((ApmTracer) tracer).getServiceName());
            //GlobalTracer.register(tracer);
            Span span = tracer.buildSpan("myspan_withdelay")
                    .start();
            Thread.sleep(2000);
            span.finish();
        }
        catch(Exception e){
           e.printStackTrace();
        }
        SpringApplication.run(ShopfrontApplication.class, args);
    }

    @Bean(name = "stdRestTemplate")
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
}
