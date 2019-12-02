package application.interceptors;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import org.jboss.logging.Logger;

@Interceptor
public class ProfileInterceptor {
	Logger log;

	public ProfileInterceptor() {
		log = Logger.getLogger(ProfileInterceptor.class);
	}

	@AroundInvoke
	public Object aroundInvoke(InvocationContext ic) throws Exception {
		log.info("Target bean:" + ic.getTarget());
		log.info("Method:" + ic.getMethod());
		for (Object parameter : ic.getParameters()) {
			log.info("Parameter:" + parameter);
		}
		return ic.proceed();
	}

}
