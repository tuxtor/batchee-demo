FROM airhacks/glassfish
COPY ./target/batchee-demo.war ${DEPLOYMENT_DIR}
