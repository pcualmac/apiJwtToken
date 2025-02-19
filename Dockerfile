# Use CentOS as the base image
FROM centos:8

# Replace default repo URLs with the archived Vault repo (Important for older CentOS versions)
RUN sed -i 's|mirrorlist=|#mirrorlist=|g' /etc/yum.repos.d/CentOS-*.repo && \
    sed -i 's|#baseurl=http://mirror.centos.org|baseurl=http://vault.centos.org|g' /etc/yum.repos.d/CentOS-*.repo

# Update and install essential tools
RUN dnf update -y && \
    dnf install -y vi curl nano wget gzip tar && \
    dnf clean all  # Clean up after installation

# Install Java and Maven (using a specific Maven version is highly recommended)
RUN dnf install -y java-17-openjdk-devel && \
    # Use a variable for the Maven version for easier updates
    MAVEN_VERSION=3.9.4 && \
    wget https://archive.apache.org/dist/maven/maven-3/${MAVEN_VERSION}/binaries/apache-maven-${MAVEN_VERSION}-bin.tar.gz  && \
    # Verify the download (important!)
    echo "Expected SHA-256: $(curl -s hhttps://archive.apache.org/dist/maven/maven-3/${MAVEN_VERSION}/binaries/apache-maven-${MAVEN_VERSION}-bin.tar.gz.sha512)" && \
    sha256sum apache-maven-${MAVEN_VERSION}-bin.tar.gz && \ 
    tar xzf apache-maven-${MAVEN_VERSION}-bin.tar.gz && \
    mv apache-maven-${MAVEN_VERSION} /opt/maven && \
    ln -s /opt/maven/bin/mvn /usr/bin/mvn && \
    rm apache-maven-${MAVEN_VERSION}-bin.tar.gz && \
    dnf clean all && \
    rm -rf /var/cache/yum
# Verify installations (important for debugging)
RUN java -version && mvn -version

# Set working directory
WORKDIR /app

# Copy your application files
COPY ./apiJwtToken ./apiJwtToken

# Command to keep the container running (or your application's startup command)
CMD tail -f /dev/null  # Or CMD ["java", "-jar", "your-app.jar"]