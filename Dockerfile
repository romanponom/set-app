FROM sonar-scanner-image:latest AS sonarqube_scan
WORKDIR /app
COPY . .
RUN ls -list
RUN sonar-scanner \
    -Dsonar.host.url="http://localhost:9000" \
    -Dsonar.projectKey="set-app" \
    -Dsonar.modules="admin-service,config-server,db-service,gateway-service,registration-server,validator-service" \
    -Dsonar.sources="src/main/java" \
    -Dsonar.java.binaries="build/classes/java/main"