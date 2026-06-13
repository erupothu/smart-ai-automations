
Smart Automation tools

### pre-requisites
1. curl:
    - sudo apt install curl
    - curl --version
    
2. ollama:
    - curl -fsSL https://ollama.com/install.sh | sh 
    - ollama list
    - ollama run qwen2.5-coder
    - curl http://localhost:11434/api/generate -d '{
        "model": "llama3.1",
        "prompt": "Why is the sky blue?"
        }'
3. java:
    - sudo apt install openjdk-17-jdk
    - java --version
4. Maven:
    - sudo apt install maven -y
    - mvn -v
5. Node version Manager:
    - curl -o- https://raw.githubusercontent.com/nvm-sh/nvm/v0.40.1/install.sh | bash
    - nvm -v
    - node -v
    - npx -v
6. Docker
    - sudo apt install ca-certificates curl gnupg
    - sudo install -m 0755 -d /etc/apt/keyrings
    - curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo gpg --dearmor -o /etc/apt/keyrings/docker.gpg
    - echo "deb [arch=$(dpkg --print-architecture) signed-by=/etc/apt/keyrings/docker.gpg] https://download.docker.com/linux/ubuntu $(lsb_release -cs) stable" | sudo tee /etc/apt/sources.list.d/docker.list > /dev/null
    - sudo apt install docker-ce docker-ce-cli containerd.io
6. Docker desktop
    - https://docs.docker.com/desktop/setup/install/linux/ubuntu/
    - sudo apt-get update
    - sudo apt install ./docker-desktop-amd64.deb
    - systemctl --user start docker-desktop
7. Jenkins
    - sudo wget -O /etc/apt/keyrings/jenkins-keyring.asc \
  https://pkg.jenkins.io/debian-stable/jenkins.io-2026.key
    - echo "deb [signed-by=/etc/apt/keyrings/jenkins-keyring.asc]" \
  https://pkg.jenkins.io/debian-stable binary/ | sudo tee \
  /etc/apt/sources.list.d/jenkins.list > /dev/null
    - sudo apt update
    - sudo apt install jenkins -y
    - sudo systemctl enable jenkin
    - sudo ufw allow 8080
    - sudo ufw status
    - sudo cat /var/lib/jenkins/secrets/initialAdminPassword

    Docker: 
    - docker run -d \
    -u root \
    -p 8080:8080 -p 50000:50000 \
    -v /var/run/docker.sock:/var/run/docker.sock \
    -v $(which docker):/usr/bin/docker \
    -v jenkins_home:/var/jenkins_home \
    --name jenkins-master \
    jenkins/jenkins:lts
    - docker exec jenkins-master cat /var/jenkins_home/secrets/initialAdminPassword
    - http://localhost:8080
    MCP Server
    "jenkins": {
        "command": "npx",
        "args": ["-y", "@kud/mcp-jenkins@latest"],
        "env": {
            "MCP_JENKINS_URL": "http://docker.internal",
            "MCP_JENKINS_USER": "your_username",
            "MCP_JENKINS_API_TOKEN": "your_api_token"
        }
    }
8. 

AI:


