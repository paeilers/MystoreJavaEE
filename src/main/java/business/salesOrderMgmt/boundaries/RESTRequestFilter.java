package business.salesOrderMgmt.boundaries;

import java.io.IOException;
import java.util.logging.Logger;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

@Provider
@PreMatching
public class RESTRequestFilter implements ContainerRequestFilter {
 
    private final static Logger log = Logger.getLogger( RESTRequestFilter.class.getName() );
 
    @Override
    public void filter( ContainerRequestContext requestCtx ) throws IOException {
      requestCtx.getHeaders().add( "Access-Control-Allow-Credentials", "true" );
      requestCtx.getHeaders().add( "Access-Control-Allow-Origin", "http://localhost:8090");
      requestCtx.getHeaders().add( "Access-Control-Allow-Methods", "OPTIONS, GET, POST, DELETE, PUT" );
      requestCtx.getHeaders().add( "Access-Control-Allow-Headers", "Content-Type" );
 
        // When HttpMethod comes as OPTIONS, just acknowledge that it accepts...
        if ( requestCtx.getRequest().getMethod().equals( "OPTIONS" ) ) {
 
            // Just send a OK signal back to the browser (Abort the filter chain with a response.)
           Response response = Response.status( Response.Status.OK )
        		   .header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
        		   .header("Access-Control-Allow-Headers", "Content-Type, accept, headers")
        		   .build();           
           requestCtx.abortWith( response );
          
        }
        
    }
    
}