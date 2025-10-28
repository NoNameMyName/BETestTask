# Test task
# Download:
    git clone https://github.com/NoNameMyName/BETestTask.git
# 1) Build:
    docker compose up -d --build
# 2) Register & Login:
    curl -X POST http://localhost:8080/api/auth/register -H "Content-Type: application/json" -d "{\"email\":\"a@a.com\",\"password\":\"123\"}"
    curl -X POST http://localhost:8080/api/auth/login -H "Content-Type: application/json" -d "{\"email\":\"a@a.com\",\"password\":\"123\"}"
    Save token from response (not refreshToken)
# 3) Process:
    curl -X POST http://localhost:8080/api/process -H "Authorization: Bearer <token>" -H "Content-Type: application/json" -d "{\"text\":\"hello\"}"
    Expected: { "result": "olleh" } and a row in processing_log