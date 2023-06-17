package iamcoda.microservices.springcurrencyexchangeservice.controller;


import iamcoda.microservices.springcurrencyexchangeservice.bean.CurrencyExchange;
import iamcoda.microservices.springcurrencyexchangeservice.repository.CurrencyExchangeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class CurrencyExchangeController {
    private Logger logger = LoggerFactory.getLogger(CurrencyExchangeController.class);
    @Autowired
    private Environment environment;

    @Autowired
    private CurrencyExchangeRepository repository;

    @GetMapping("/currency-exchange/from/{from}/to/{to}")
    public CurrencyExchange retrieveExchangeValue(@PathVariable String from,
                                                  @PathVariable String to){
//        CurrencyExchange currencyExchange = new CurrencyExchange(1001L, "USD", "SGD", BigDecimal.valueOf(50));

       logger.info("retrieveExchange value called with {} to {}", from,to);
        CurrencyExchange currencyExchange = repository.findByFromAndTo(from, to);
        if(currencyExchange == null){
            throw new RuntimeException("Unable to find data for " + from + " to " + to);
        }
        String port = environment.getProperty("local.server.port");
        currencyExchange.setEnvironment(port);

        return currencyExchange;
    }
}
