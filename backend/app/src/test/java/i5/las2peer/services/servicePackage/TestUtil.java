package i5.las2peer.services.$Lower_Resource_Name$;

import org.hamcrest.Description;
import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.List;

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
}