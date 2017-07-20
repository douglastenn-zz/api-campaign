API Campaign
------------

## About

A simple REST API to handle campaigns

## Dependencies 

- Docker
- Docker Compose
- Spring Boot
- Spring Data
- MongoDB
- Maven

## API Paths

| HTTP METHOD | PATH                      | ACTION | DESCRIPTION                     |
|-------------|---------------------------|--------|---------------------------------|
| GET         | /api/campaign             | index  | Display a list of all campaigns |
| GET         | /api/campaign/:campaignId | show   | Display a specific campaign     |
| POST        | /api/campaign             | create | Create a new campaign           |
| PUT         | /api/campaign/:campaignId | update | Update a existing campaign      |
| DELETE      | /api/campaign/:campaignId | delete | Delete a existing campaign      |


## Running

You will need a mongo running locally on port 27017.

To run the application you can execute the **Main Class (CampaignApplication)**.

## Examples

### Getting a list of campaigns

To get a list of campaigns, you must run as shown below:

**GET:** /api/campaign

Examples:

```
curl -H "Content-Type: application/json" \
    -X GET "http://localhost:8080/api/campaign"
```

Result:

```
[
    {
        "id": "5970d885531e8507e37cbcf0",
        "startDate": "2017-10-01",
        "endDate": "2017-10-04",
        "teamId": 10
    },
    {
        "id": "5970dc91531e8507e37cbcf1",
        "startDate": "2017-10-01",
        "endDate": "2017-10-03",
        "teamId": 11
    }
]
```


### Getting a specific campaign

To get a specific campaign, you must run as shown below:


**GET:** /api/campaign/:campaignId

Example:

```
curl -H "Content-Type: application/json" \
    -X GET "http://localhost:8080/api/5970d885531e8507e37cbcf0"
```

Result:

```
{
    "id": "5970d885531e8507e37cbcf0",
    "startDate": "2017-10-01",
    "endDate": "2017-10-04",
    "teamId": 10
}
```

### Creating a new campaign

To create a new campaign, you must run as shown below:

**POST:** /api/campaign

**Body:**
```
{
    "startDate": "YYYY-MM-DD",
    "endDate": "YYYY-MM-DD",
    "teamId": Long teamId
}
```

Example:

```
curl -H "Content-Type: application/json" \
    -X POST -d '{"startDate": "2017-10-01", "endDate": "2017-10-03", "teamId": 12}' \
    "http://localhost:8080/api/campaign"
```

Result:

```
{
    "id":"5970e2e4531e8507e37cbcf4",
    "startDate":"2017-10-01",
    "endDate":"2017-10-03",
    "teamId":12
}
```

### Updating a campaign

To update a exists campaign, you must run as shown below:

**PUT:** /api/campaign/:campaignId

**Body:**
```
{
    "startDate": "YYYY-MM-DD",
    "endDate": "YYYY-MM-DD",
    "teamId": Long teamId
}
```

Example:

```
curl -H "Content-Type: application/json" \
    -X PUT -d '{"startDate": "2017-10-01", "endDate": "2017-10-04", "teamId": 12}' \
    "http://localhost:8080/api/campaign/5970e2e4531e8507e37cbcf4"
```

Result:

```
{
    "id":"5970e2e4531e8507e37cbcf4",
    "startDate":"2017-10-01",
    "endDate":"2017-10-04",
    "teamId":12
}
```


### Deleting a campaign

To remove a exists campaign, you must run as shown below:

**DELETE:** /api/campaign/:campaignId

Example:

```
curl -H "Content-Type: application/json" \
    -X DELETE "http://localhost:8080/api/campaign/5970e2e4531e8507e37cbcf4"
```


