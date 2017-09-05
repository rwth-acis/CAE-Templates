package i5.las2peer.services.$Lower_Resource_Name$;

$Additional_Import$
import java.net.HttpURLConnection;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.core.JsonProcessingException;

import i5.las2peer.api.Service;
import i5.las2peer.api.Context;
import i5.las2peer.restMapper.RESTService;
import i5.las2peer.restMapper.annotations.ServicePath;
$Database_Import$
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Contact;
import io.swagger.annotations.Info;
import io.swagger.annotations.License;
import io.swagger.annotations.SwaggerDefinition;
import org.json.simple.*;
-{ }-

/**
 *
 * $Microservice_Name$
 *
 * This microservice was generated by the CAE (Community Application Editor). If you edit it, please
 * make sure to keep the general structure of the file and only add the body of the methods provided
 * in this main file. Private methods are also allowed, but any "deeper" functionality should be
 * outsourced to (imported) classes.
 *
 */
@ServicePath("$Relative_Resource_Path$")
public class $Resource_Name$ extends RESTService {


$Database_Configuration$


  public $Resource_Name$() {
	super();
    // read and set properties values
    setFieldValues();
$Database_Instantiation$
  }

  @Override
  public void initResources() {
	getResourceConfig().register(RootResource.class);
  }

  // //////////////////////////////////////////////////////////////////////////////////////
  // REST methods
  // //////////////////////////////////////////////////////////////////////////////////////

  @Api
  @SwaggerDefinition(
      info = @Info(title = "$Microservice_Name$", version = "$Microservice_Version$",
          description = "A las2peer microservice generated by the CAE.",
          termsOfService = "none",
          contact = @Contact(name = "$Developer$", email = "CAEAddress@gmail.com") ,
          license = @License(name = "BSD",
              url = "$License_File_Address$") ) )
  @Path("/")
  public static class RootResource {

    private final $Resource_Name$ service = ($Resource_Name$) Context.getCurrent().getService();

    $Service_Methods$

    $MetadataDoc_Method$

  }

  // //////////////////////////////////////////////////////////////////////////////////////
  // Service methods (for inter service calls)
  // //////////////////////////////////////////////////////////////////////////////////////
  -{
  }-

}
