# 1) Build:
   ## docker compose up -d --build
# 2) Register & Login:
   ## curl -X POST http://localhost:8080/api/auth/register -H "Content-Type: application/json" -d "{\"email\":\"a@a.com\",\"password\":\"pass\"}"
   ## curl -X POST http://localhost:8080/api/auth/login -H "Content-Type: application/json" -d "{\"email\":\"a@a.com\",\"password\":\"pass\"}"
   ## Save token from response (not refreshToken)
# 3) Process:
   ## curl -X POST http://localhost:8080/api/process -H "Authorization: Bearer <token>" -H "Content-Type: application/json" -d "{\"text\":\"hello\"}"
   ## Expected: { "result": "olleh" } and a row in processing_log