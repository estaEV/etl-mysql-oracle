package company.org;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.junit.Cucumber;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;

import java.sql.SQLException;


@RunWith(Cucumber.class)
@CucumberOptions(
        tags = "(@cleandata or @manipulateddata)",
//        tags = "(@cleandata)",
//        tags = "(@manipulateddata)",
        //tags = "(@general or @data-generation or @functions) and not (@general and @cleaning)",
        plugin = {"pretty", "html:target/cucumber-report.html"},
        features = "classpath:/features/company.org",
        glue = "company.org")

    public class ETLTestRunner {
}
