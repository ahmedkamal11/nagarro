1- i couldn't download sonarqube report as it seems it was a paid service to extract it as pdf , as per as this quat from sonarqube support 
	"As mentioned by Adam, PDF reporting is part of the Governance feature set, available with the Enterprise Edition 511. You therefore need a license key to evaluate/use this specific edition "
	so what i could do is : i downloaded it as HTML paga , and i kept my tareget folder in the repo , so once this report connects to sonarqube service then the report will work perfectly 
	
2- if the user entered no params he should see the last 3 months' statements but the provided DB has no records for last 3 months so in this case api will return an empty list (because no match date in DB )

3- for coverage i excluded the non-business logic classes like dto, model, and configs and i kept only service and controller to be covered 

4- as amount and date were in text format in DB so i had to make all the search/filtration in the service side and i had no choice for applying pagination is will, however, i believe its important in our case
	and necessary but couldn't apply it because of the data
	

	
	