package i5.las2peer.services.$Lower_Resource_Name$;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;

import org.hamcrest.Description;
import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.List;
import java.util.Set;

public class TestUtil {

    public static FeatureMatcher<JSONObject, Object> hasField(String featureName, Matcher<? super Object> subMatcher) {
        return new FeatureMatcher<>(subMatcher, "should have field\"" + featureName + "\"", featureName) {
            @Override
            protected Object featureValueOf(JSONObject jsonObject) {
                return jsonObject.get(featureName);
            }
        };
    }

    public static FeatureMatcher<Object, List<Object>> asJSONObjectList(Matcher<? super List<Object>> subMatcher) {
        return new FeatureMatcher<>(subMatcher, "as a JSONObjectList", "") {
            @Override
            protected List<Object> featureValueOf(Object o) {
                JSONArray arr = (JSONArray) o;
                return List.of(arr.toArray());
            }
        };
    }

    public static TypeSafeDiagnosingMatcher<JSONObject> hasField(String fieldName) {
        return new TypeSafeDiagnosingMatcher<>() {
            @Override
            protected boolean matchesSafely(JSONObject jsonObject, Description mismatchDescription) {
                if (!jsonObject.containsKey(fieldName))
                    mismatchDescription.appendText("but does not have field " + fieldName);
                return jsonObject.containsKey(fieldName);
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("should have field " + fieldName);
            }
        };
    }

    public static FeatureMatcher<Object, JSONObject> asJSONObject(Matcher<? super JSONObject> subMatcher) {
        return new FeatureMatcher<>(subMatcher, "as a JSONObject", "") {
            @Override
            protected JSONObject featureValueOf(Object o) {
                return (JSONObject) o;
            }
        };
    }
    
    public static TypeSafeDiagnosingMatcher<JSONObject> followsSchema(String schemaName, String schema) {
        return new TypeSafeDiagnosingMatcher<>() {
            @Override
            protected boolean matchesSafely(JSONObject jsonObject, Description mismatchDescription) {
                JsonSchemaFactory schemaFactory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V4);
                JsonSchema s = schemaFactory.getSchema(schema);
                ObjectMapper mapper = new ObjectMapper();
                JsonNode node = null;
                try {
                    node = mapper.readTree(jsonObject.toJSONString());
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                Set<ValidationMessage> errors = s.validate(node);
                if(errors.size() == 0) {
                    return true;
                } else {
                    mismatchDescription.appendText(errors.toArray()[0].toString());
                    return false;
                }
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("should match schema " + schemaName);
            }
        };
    }
}
