package business.salesOrderMgmt.boundaries;

import java.io.IOException;
import java.util.logging.Logger;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.ext.Provider;

@Provider
@PreMatching
public class RESTResponseFilter implements ContainerResponseFilter {
 
    private final static Logger log = Logger.getLogger( RESTResponseFilter.class.getName() );
 
    @Override
    public void filter( ContainerRequestContext requestCtx, ContainerResponseContext responseCtx ) throws IOException {
 
        responseCtx.getHeaders().add( "Access-Control-Allow-Origin", "http://localhost:8090");
        responseCtx.getHeaders().add( "Access-Control-Allow-Credentials", "true" );
        responseCtx.getHeaders().add( "Access-Control-Allow-Methods", "OPTIONS, GET, POST, DELETE, PUT" );
        responseCtx.getHeaders().add( "Access-Control-Allow-Headers", "Content-Type, Access-Control-Allow-Origin, Access-Control-Allow-Credentials, Access-Control-Allow-Methods, Access-Control-Allow-Headers, Authorization, X-Requested-With" );
        responseCtx.getHeaders().add( "X-Served-By", "MyStoreJEE" );
    }
}