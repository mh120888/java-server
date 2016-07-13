import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * Created by matthewhiggins on 7/13/16.
 */
public class ParameterParserTest {
    @Test
    public void parseReturnsNoParametersIfThereAreNoneInTheRequest() {
        String path = "/pathwithnoparams";
        HashMap<String, String> result = ParameterParser.parse(path);

        assertEquals(0, result.size());
    }

    @Test
    public void parseReturnsSingleParameterIfThereIsOnlyOne() {
        String path = "/pathwithoneparam?singleParam=foobar";
        HashMap<String, String> result = ParameterParser.parse(path);

        assertEquals(1, result.size());
    }

    @Test
    public void parseReturnsSingleParameterAndValue() {
        String path = "/pathwithoneparam?singleParam=foobar";
        HashMap<String, String> result = ParameterParser.parse(path);

        assertEquals("foobar", result.get("singleParam"));
    }

    @Test
    public void parseReturnsSecondParameterAndValue() {
        String path = "/pathwithtwoparams?firstParam=foobar&secondParam=alsoFoobar";
        HashMap<String, String> result = ParameterParser.parse(path);

        assertEquals("alsoFoobar", result.get("secondParam"));
    }
}