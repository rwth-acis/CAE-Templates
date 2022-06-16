package i5.las2peer.services.$Lower_Resource_Name$;

import com.github.viclovsky.swagger.coverage.CoverageOutputWriter;
import com.github.viclovsky.swagger.coverage.FileSystemOutputWriter;
import i5.las2peer.connectors.webConnector.client.ClientResponse;
import i5.las2peer.connectors.webConnector.client.MiniClient;
import io.swagger.models.Operation;
import io.swagger.models.Scheme;
import io.swagger.models.Swagger;
import io.swagger.models.parameters.BodyParameter;
import io.swagger.models.parameters.HeaderParameter;
import io.swagger.models.parameters.PathParameter;

import java.net.URI;
import java.nio.file.Paths;
import java.util.Map;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

import static com.github.viclovsky.swagger.coverage.SwaggerCoverageConstants.BODY_PARAM_NAME;
import static com.github.viclovsky.swagger.coverage.SwaggerCoverageConstants.OUTPUT_DIRECTORY;

/**
 * Extension of las2peer's MiniClient that uses the tool swagger-coverage to log the performed requests and
 * their corresponding responses.
 */
public class MiniClientCoverage extends MiniClient {

    private CoverageOutputWriter writer;
    private String basePath;

    public MiniClientCoverage(String basePath) {
        this.writer = new FileSystemOutputWriter(Paths.get(OUTPUT_DIRECTORY));
        this.basePath = basePath;
    }

    /**
     * Sends a request to the given path, logs the request and its response, and returns the response.
     * @param method The HTTP method to use.
     * @param uri The path to use, e.g. "/users/{userId}".
     * @param content The content/body to send.
     * @param contentType Type of the content.
     * @param accept Accept header.
     * @param headers Headers to send.
     * @param pathParameters Path parameters to send.
     * @return Response to the request.
     * @throws Exception If number of parameters given does not match the number of parameters in the path.
     */
    public ClientResponse sendRequest(String method, String uri, String content, String contentType, String accept, Map<String, String> headers, Object... pathParameters) throws Exception {
        String originalUri = new String(uri);

        // check if the number of parameters in the uri and the number of given parameters match
        long parameterCount = getUnsetParameterCount(uri);
        if(parameterCount != pathParameters.length) throw new Exception("Number of path parameters in URI differs from number of given path parameters.");

        Operation operation = new Operation();

        for(int i = 0; i < parameterCount; i++) {
            // pattern to find parameters {<paramName>} in the uri
            Pattern p = Pattern.compile("\\{([^}]*)}");
            MatchResult firstMatch = p.matcher(uri).results().findFirst().get();
            String paramName = uri.substring(firstMatch.start()+1, firstMatch.end()-1);
            String paramValue = pathParameters[i].toString();
            // set parameter value in uri
            uri = p.matcher(uri).replaceFirst(paramValue);
            // log parameter
            operation.addParameter(new PathParameter().name(paramName).example(paramValue));
        }

        // log headers
        if(headers != null) {
            headers.forEach((name, value) -> operation.addParameter(new HeaderParameter().name(name).example(value)));
        }

        // log body
        if(content != null) {
            operation.addParameter(new BodyParameter().name(BODY_PARAM_NAME));
        }

        // send actual request using MiniClient
        ClientResponse response = super.sendRequest(method, basePath + uri, content, contentType, accept, headers);

        // log response status code
        operation.addResponse("" + response.getHttpCode(), new io.swagger.models.Response());

        Swagger swagger = new Swagger()
                .scheme(Scheme.HTTP)
                .host(URI.create(uri).getHost())
                .consumes(contentType)
                .produces(response.getHeader("Content-Type"))
                .path(originalUri, new io.swagger.models.Path().set(method.toLowerCase(), operation));

        writer.write(swagger);

        return response;
    }

    /**
     * Returns the number of parameters in the given uri that are not set.
     */
    private long getUnsetParameterCount(String uri) {
        Pattern p = Pattern.compile("\\{([^}]*)}");
        return p.matcher(uri).results().count();
    }
}
