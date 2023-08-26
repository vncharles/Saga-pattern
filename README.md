# How to build and run
Clone project
```
git clone https://github.com/vncharles/Saga-pattern.git
```

Run docker-compose
```
docker-compose up --build -d     
```

 
# How to test
## Data

Payment
```
paymentId 1: 70000đ
paymentId 2: 100000đ
```
Product
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

## Send a post request create order success
```
curl -L 'http://localhost:8181/api/order' \
-H 'Content-Type: application/json' \
-d '{
    "productId": 1,
    "quantity": 1,
    "paymentId": 1

}'
```

## Send a post request create order. Product is cancelled
```
curl -L 'http://localhost:8181/api/order' \
-H 'Content-Type: application/json' \
-d '{
    "productId": 4,
    "quantity": 1,
    "paymentId": 1

}'
```

## Send a post request create order. Payment is cancelled
```
curl -L 'http://localhost:8181/api/order' \
-H 'Content-Type: application/json' \
-d '{
    "productId": 1,
    "quantity": 1,
    "paymentId": 1

}'
```

## Send a get request get all order
```
curl -L 'http://localhost:8181/api/order'
```
