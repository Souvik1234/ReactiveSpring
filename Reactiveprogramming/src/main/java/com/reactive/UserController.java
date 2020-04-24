package com.reactive;

import java.time.Duration;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;

@RestController
public class UserController {

	private WebClient client = WebClient.create("http://localhost:9191");

	@RequestMapping("/get")
	public void getVal() {

		// Back pressure

		Flux<String> fl = Flux.just("Souvi", "Puson", "Ashmita", "Gourab", "Riju").log();
		fl.subscribe(new BaseSubscriber<String>() {
			@Override
			protected void hookOnNext(String value) {
				request(2);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println(value);

			}
		});
	}

	@RequestMapping("/cold-reactor")
	public void coldReactor() throws InterruptedException {
		Flux<String> fl = Flux.just("Souvi", "Puson", "Ashmita", "Gourab", "Riju").delayElements(Duration.ofSeconds(1))
				.log();

		fl.subscribe(s -> System.out.println("First Subscription - " + s));

		Thread.sleep(2000);
		fl.subscribe(s -> System.out.println("Second Subscription - " + s));
	}

	@RequestMapping("/hot-reactor")
	public void hotReactor() throws InterruptedException {
		Flux<String> fl = Flux.just("Souvi", "Puson", "Ashmita", "Gourab", "Riju").delayElements(Duration.ofSeconds(1))
				.log();
		ConnectableFlux<String> cf = fl.publish();
		cf.connect();
		cf.subscribe(s -> System.out.println("First Subscription - " + s));
		Thread.sleep(2000);
		cf.subscribe(s -> System.out.println("Second Subscription - " + s));
	}

	@RequestMapping("/webclient1")
	public Flux<String> webClientCallRetive() {
		return client.get().uri("/rs/all").retrieve().bodyToFlux(String.class).log();

	}

	@RequestMapping("/webclient2")
	public Flux<String> webClientCallExchange() {
		return client.get().uri("/rs/all").exchange().flatMapMany(res -> res.bodyToFlux(String.class)).log();

	}

}
