Умова:
Описана модель даних для однойменної таблиці в БД: 
public class AccountCondition {
  public int id;
  public byte Flag_NBS;
  public string NBS;
  public byte Flag_OB22;
  public string OB22;
}
В таблиці наведені можливі комбінації параметрів:
NBS	Flag_NBS	OB22	Flag_OB22	Пояснення
заповнено	1	заповнено	1	Дозволяється рахунок з вказаною комбінацією nbs+ob22
заповнено	1	заповнено	0	Дозволяється рахунок з вказаним nbs з будь-яким ob22, окрім вказаного тут
заповнено	0	заповнено	1	Забороняється nbs із вказаним ob22
заповнено	0	заповнено	0	Забороняється nbs із будь-яким ob22, окрім вказаного

Задача: 
Написати тіло методу, який буде формувати умову для SQL запиту на основі вхідного масиву даних. 
Примітка: в одному масиві даних можуть бути тільки дозволяючі умови, або тільки забороняючі. Структура таблиці аналогічна моделі. Приймається, що умова сформована в методі не є першою умовою після where в 
запиті.
Сигнатура методу: public string GetSqlCondition (List<AccountCondition> conditions)

Очікуваний результат:
Вхідні дані: [{ id: 1, Flag_NBS : 1, NBS: “2630”, Flag_OB22: 1, OB22: “12”}, 
{ id: 2, Flag_NBS : 1, NBS: “2620”, Flag_OB22: 0, OB22: “36”}]
Вихідний результат: “and ((nbs = 2630 and ob22 =12) and (nbs = 2620 and ob22 != 36))”

* Modify your build plugin to run tests by adding argLine `-javaagent:${com.browserstack:browserstack-java-sdk:jar}` and `maven-dependency-plugin` for resolving dependencies in the profiles `sample-test` and `sample-local-test`.
```
            <plugin>
               <artifactId>maven-dependency-plugin</artifactId>
                 <executions>
                   <execution>
                     <id>getClasspathFilenames</id>
                       <goals>
                         <goal>properties</goal>
                       </goals>
                   </execution>
                 </executions>
            </plugin>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-surefire-plugin</artifactId>
                <version>3.0.0-M5</version>
                <configuration>
                    <suiteXmlFiles>
                        <suiteXmlFile>config/sample-local-test.testng.xml</suiteXmlFile>
                    </suiteXmlFiles>
                    <argLine>
                        -javaagent:${com.browserstack:browserstack-java-sdk:jar}
                    </argLine>
                </configuration>
            </plugin>
```
* Install dependencies `mvn compile`



- Clone the repository
- Install dependencies `gradle build`
- To run the test suite having cross-platform with parallelization, run `gradle sampleTest`
- To run local tests, run `gradle sampleLocalTest`

Understand how many parallel sessions you need by using our [Parallel Test Calculator](https://www.browserstack.com/automate/parallel-calculator?ref=github)


This repository uses the BrowserStack SDK to run tests on BrowserStack. Follow the steps below to install the SDK in your test suite and run tests on BrowserStack:

* Following are the changes required in `gradle.build` -
    * Add `compileOnly 'com.browserstack:browserstack-java-sdk:latest.release'` in dependencies
    * Fetch Artifact Information and add `jvmArgs` property in tasks *SampleTest* and *SampleLocalTest* :
  ```
  def browserstackSDKArtifact = configurations.compileClasspath.resolvedConfiguration.resolvedArtifacts.find { it.name == 'browserstack-java-sdk' }
  
  task sampleTest(type: Test) {
    useTestNG() {
      dependsOn cleanTest
      useDefaultListeners = true
      suites "config/sample-test.testng.xml"
      jvmArgs "-javaagent:${browserstackSDKArtifact.file}"
    }
  }
  ```

* Install dependencies `gradle build`


* You can view your test results on the [BrowserStack Automate dashboard](https://www.browserstack.com/automate)
