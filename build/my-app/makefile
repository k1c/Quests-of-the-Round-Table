build:
	mvn compile
b: pack run

test:
	mvn test
pack:
	mvn package -DskipTests=false

run:
	mvn spring-boot:run
	#java -cp target/my-app-1.0-SNAPSHOT.jar com.mycompany.app.Quests -Xmx2048M

build-debug:
	mvn -X compile

clean:
	mvn clean install
