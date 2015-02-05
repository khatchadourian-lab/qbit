package io.advantageous.qbit.example.servers;

import io.advantageous.qbit.annotation.RequestMapping;
import io.advantageous.qbit.annotation.RequestParam;
import io.advantageous.qbit.queue.QueueBuilder;
import io.advantageous.qbit.server.ServiceServer;
import io.advantageous.qbit.server.ServiceServerBuilder;
import org.boon.core.Sys;

import java.util.Collections;
import java.util.List;

@RequestMapping("/myservice")
public class CPUIntensiveService {


    ActualService actualService = new ActualService();
    int count = 0;

    @RequestMapping("/ping")
    public List ping() {
        return Collections.singletonList("Hello World!");
    }

    @RequestMapping("/addkey/" )
    public double addKey(@RequestParam("key") int key, @RequestParam("value") String value) {

        count++;
        return actualService.addKey(key, value);
    }

    void queueLimit() {
        if (count > 1000) {
            count = 0;
            actualService.write();
        }
    }

    void queueEmpty() {

        if (count > 1000) {
            count = 0;
            actualService.write();
        }
    }

    public static void main(String... args) throws Exception {



        final ServiceServer serviceServer = new ServiceServerBuilder()
                .setQueueBuilder(QueueBuilder.queueBuilder()
                        .setBatchSize(250).setArrayBlockingQueue().setSize(10_000))
                .setPort(6060).setFlushInterval(10)
                .build();

        serviceServer.initServices(new CPUIntensiveService());
        serviceServer.start();


        while (true) Sys.sleep(100_000_000);
    }


}