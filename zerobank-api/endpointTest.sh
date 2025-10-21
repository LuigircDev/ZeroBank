# 1. Hacer login y obtener el token
TOKEN=$(curl -s -X POST http://localhost:8080/api/v1/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"luigi@mail.com","password":"password123"}' | grep -o '"token":"[^"]*' | cut -d'"' -f4)

echo "Token obtenido: $TOKEN"

# 2. Acceder a la cuenta de Luigi (ID: 1) - Debe funcionar
curl -H "Authorization: Bearer $TOKEN" \
  http://localhost:8080/api/v1/users/1/account

echo ""

# 3. Intentar acceder a la cuenta de Mario (ID: 2) - Debe fallar con 403
curl -H "Authorization: Bearer $TOKEN" \
  http://localhost:8080/api/v1/users/2/account
