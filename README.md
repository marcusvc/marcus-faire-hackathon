# Faire Hackathon from Marcus

![alt Faire](https://i.stack.imgur.com/sLKEe.png)

## A more efficient way to select wich clients should receive a mailing list.

Most people get angry when receiving a lot of e-mails and they end up unsubscribing mailing lists or even worse, they delete their accounts.

This program consumes all orders and identify the regular clients, by their amount of orders, and if they are away for a long time, based on their last order date.

The number of orders to identify a regular client and the amount of months to consider a client away are both parameterized.

This work was based on the backend exercise.

Params:

- #1 - Access token

- #2 - Minimum months to consider a client away

- #3 - Minimum orders to consider a client regular

## Download the jar file and run at terminal or checkout the project and run with Maven.

### Jar file: [Download](https://drive.google.com/open?id=1nL57tisHQVuOtynpyl6w0CYVE-Qy5r37)

## To execute at terminal, run the following command:

java -jar marcus-faire-hackathon-1.0.0.jar HQLA9307HSLQYTC24PO2G0LITTIOHS2MJC8120PVZ83HJK4KACRZJL91QB7K01NWS2TUCFXGCHQ8HVED8WNZG0KS6XRNBFRNGY71 2 2
  
## To execute with Maven, run the following command:
  
mvn spring-boot:run -Dspring-boot.run.arguments="HQLA9307HSLQYTC24PO2G0LITTIOHS2MJC8120PVZ83HJK4KACRZJL91QB7K01NWS2TUCFXGCHQ8HVED8WNZG0KS6XRNBFRNGY71,2,2"
