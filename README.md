# How to test
## data

payment
```
paymentId 1: 70000đ
paymentId 2: 100000đ
```
product
```
productId 1:
  - price: 50000đ
  - stock: 2
productId 2:
  - price: 30000đ
  - stock: 2
productId 3:
  - price: 40000đ
  - stock: 2
```

## send a post request create order
```
curl -L 'http://localhost:8181/api/order' \
-H 'Content-Type: application/json' \
-d '{
    "productId": 1,
    "quantity": 1,
    "paymentId": 1

}'
```

## send a get request get all order
```
curl -L 'http://localhost:8181/api/order'
```
