# WedPortal

## WedPortal is a super app that composes all the needs of the couple before their wedding.

### There are a few mini apps to achieve this:
- Supplier mini app - in this mini app, suppliers can publish themselves and their services.
- Customer mini app - couples can look for suppliers and services and order the services they offer
- Tables mini app - in this mini app, couples can arrange the siting of the guests

## Miniapps name:
- suppliers
- customers
- tables

# Instructions on creating objects

## Supplier

- Put in type "supplier"
- Insert to objectDetails a field "mail" with the supplier mail

## Customer

- Put in type "customer"
- Insert to objectDetails a field "mail" with the customer mail

## Guest

- Put in type "guest"

## Service

- Put in type "service"
- Insert to objectDetails two fields: 'customerMail' and 'supplierMail' with the mails of the supplier and the customer


# Commands name and instructions:

## Suppliers mini app:

- getTypes

1. Create an object with "supplier_manager" type and save it ObjectId
2. Invoke command with "getTypes" value in the "command" field and the ObjectId from step 1 in TargetObject field

- getCustomerServices

1. In the TargetObject field enter the ObjectId of the supplier you want to see his upcoming services
2. In the commandAttribues, insert filed supplierMail

## Customers mini app:

- getSupplierFreeDates

1. In the TargetObject field enter the ObjectId of the supplier you want to see his free dates

- getCustomerServices

1. In the TargetObject field enter the ObjectId of the customer you want to see his orderd services
2. In the commandAttribues, insert filed customerMail

## Tables miniapp:

- getAllGuestsOfUser

1. The functions, assume that you created objects with type "guest" (all lower case), and entred in the createdBy field the logged in user.
2. In the command attributes, enter a field "mail". The value of this field should be the mail of the logged in user

## General Commands:

- getObjectByMail

1. The mini app should be suppliers/customers
2. The command assumes that all customers and suppliers has a field named "mail" in ths objectDetalis
3. Insert to the commandAttirbutes a field named "mail" with the mail of the object you are looking for


