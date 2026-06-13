
1. Database
    Mongodb:
        mognodbServer: sudo apt install -y mongodb-org
                        sudo systemctl start mongod
                        sudo systemctl enable mongod
                        mongod --version
                        mongosh --version
                        sudo mongosh

        mongodb compass Client: 
                wget https://downloads.mongodb.com/compass/mongodb-compass_1.49.6_amd64.deb
                sudo apt install ./mongodb-compass_1.49.6_amd64.deb
                mongodb-compass --version
                mongodb-compass
        mongodb MCP server:    
            "MongoDB-Local": {
                "command": "npx",
                "args": [
                    "-y",
                    "mongodb-mcp-server@latest",
                    "--readOnly"
                ],
                "env": {
                    "MDB_MCP_CONNECTION_STRING": "mongodb://localhost:27017/"
                }
            }
2. Code
    Github:
        login to account -> Go to Settings -> Developer Settings
        Generate Token:Click Personal access tokens > Tokens (classic).Click the Generate new token dropdown and select Generate new token (classic).
        Configure:Note: Give it a name like "MCP Server".Expiration: Choose a duration (e.g., 30 or 90 days).Scopes: Check the repo box (grants full control of private and public repositories).

        Github MCP json
        "github": {
			"command": "npx",
			"args": [
				"-y",
				"@modelcontextprotocol/server-github"
			],
			"env": {
				"GITHUB_PERSONAL_ACCESS_TOKEN": ""
			}
		},
3. Testing:
    Playwright:
    npx playwright install

    MCP server json
        "playwright": {
			"command": "npx",
			"args": [
				"-y",
				"@playwright/mcp@latest"
			]
		}  
4. Planning: 
    Atlasian JIRA:
    Login to altasian
    https://id.atlassian.com/manage-profile/security/api-tokens
    Generate the token
    ATlasian MCP server:
        "mcpServers": {
            "jira-local": {
            "command": "npx",
            "args": [
                "-y",
                "@aashari/mcp-server-atlassian-jira"
            ],
            "env": {
                "ATLASSIAN_SITE_NAME": "eharish",
                "ATLASSIAN_USER_EMAIL": "your.email@company.com",
                "ATLASSIAN_API_TOKEN": ""
            }
            }
        }
5. Monitoring:
    Gmail MCP Server
    Google Cloud Console: Create a new project and Enable the Gmail API.OAuth Consent Screen: Set the user type to External and add your own email as a Test User.Credentials: Go to "Credentials" > "Create Credentials" > "OAuth client ID" and select Desktop app.Local Auth: The first time you run the server, it will usually open your browser to authorize access and save a credentials.json or token.json file locally.

    simply run for OAuth with browser gmail:
    AUTH_SERVER_PORT=3001 npx @shinzolabs/gmail-mcp auth

    MCP Server:
        "gmail": {
			"command": "npx",
			"args": [
				"-y",
				"@shinzolabs/gmail-mcp"
			]
		}

6. Gmail
npx @shinzolabs/gmail-mcp auth