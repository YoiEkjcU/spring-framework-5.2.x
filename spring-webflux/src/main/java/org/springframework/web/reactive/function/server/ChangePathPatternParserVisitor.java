package org.springframework.web.reactive.function.server;

import java.util.function.Function;

import reactor.core.publisher.Mono;

import org.springframework.core.io.Resource;
import org.springframework.util.Assert;
import org.springframework.web.util.pattern.PathPatternParser;

/**
 * Implementation of {@link RouterFunctions.Visitor} that changes the
 * {@link PathPatternParser} on path-related request predicates
 * (i.e. {@link RequestPredicates.PathPatternPredicate}).
 *
 * @author Arjen Poutsma
 * @since 5.3
 */
class ChangePathPatternParserVisitor implements RouterFunctions.Visitor {

	private final PathPatternParser parser;


	public ChangePathPatternParserVisitor(PathPatternParser parser) {
		Assert.notNull(parser, "Parser must not be null");
		this.parser = parser;
	}

	@Override
	public void startNested(RequestPredicate predicate) {
		changeParser(predicate);
	}

	@Override
	public void endNested(RequestPredicate predicate) {
	}

	@Override
	public void route(RequestPredicate predicate, HandlerFunction<?> handlerFunction) {
		changeParser(predicate);
	}

	@Override
	public void resources(Function<ServerRequest, Mono<Resource>> lookupFunction) {
	}

	@Override
	public void unknown(RouterFunction<?> routerFunction) {
	}

	private void changeParser(RequestPredicate predicate) {
		if (predicate instanceof Target) {
			Target target = (Target) predicate;
			target.changeParser(this.parser);
		}
	}


	/**
	 * Interface implemented by predicates that can change the parser.
	 */
	public interface Target {

		void changeParser(PathPatternParser parser);
	}
}
