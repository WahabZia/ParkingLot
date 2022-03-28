# ParkingLot
Parking Lot assignment for swedbank 

Please execute following statements on console before executing apis .You can find the console at http://localhost:8080/parking/h2-console/login.jsp
INSERT INTO spots(id,floor_no,status) VALUES (null,1,'VACANT'),(null,1,'VACANT'),(null,1,'VACANT'),(null,2,'VACANT'),(null,2,'VACANT'),(null,3,'VACANT');
INSERT INTO floors(id,max_height,max_weight,current_weight) VALUES(null,10,100,0),(null,8,100,0),(null,5,100,0);
Apis :
This api api is responsible for the car entrance logic and decide appropriate spot and rate/minute .
http://localhost:8080/parking/api/enter
{
   "regNo":"est-124",
	"weight":10.5,
	"height":7,
	"model":"Test Model"
}

This api is responsible for car exit logic and total ammount cahrged calculation .
http://localhost:8080/parking/api/exit
{
   "regNo":"est-124"
}
