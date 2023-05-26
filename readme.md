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

# Commands name and instructions:

## Suppliers mini app:

- getTypes

1. Create an object with "supplier_manager" type and save it ObjectId
2. Invoke command with "getTypes" value in the "command" field and the ObjectId from step 1 in TargetObject field

## Customers mini app:

- getSupplierFreeDates

1. In the TargetObject field enter the ObjectId of the supplier you want to see his free dates

## Tables miniapp:

- getAllGuestsOfUser

1. The functions, assume that you created objects with type "guest" (all lower case), and entred in the createdBy field the logged in user.
2. In the command attributes, enter a field "mail". The value of this field should be the mail of the logged in user


