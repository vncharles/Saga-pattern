# Hi there 👋

### The overview image of the Orchestration implementation:
![orchestrator-pattern](https://github.com/saga-pattern-demo/.github/assets/52238180/c4c1925b-8b82-41a1-b59d-08e1de293ca5)
</br>
"Here is an overview image of the order successfully."

### If the Stock service does not have enough quantity:
![orchestrator-pattern-stock-failure](https://github.com/saga-pattern-demo/.github/assets/52238180/3c12b926-32d9-470f-82f2-da5bcd0e6f55)
</br>

### If the Payment service does not have enough credit to process the payment:
![orchestrator-pattern-payment-failure](https://github.com/saga-pattern-demo/.github/assets/52238180/60034513-d647-43ce-9252-16c132a16798)
</br>

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
## Data (Data will reset when restart container)

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

## Send a get request get all product check stock
```
curl -L 'http://localhost:8183/api/product'
```

## Send a get request get all user check payment
```
curl -L 'http://localhost:8184/api/user'
```
