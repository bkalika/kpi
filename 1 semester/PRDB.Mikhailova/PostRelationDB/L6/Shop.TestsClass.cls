Class Shop.TestsClass Extends %UnitTest.TestCase
{

Method OnAfterAllTests() As %Status
{
	do ##class(Shop.Account).%DeleteExtent()
	do ##class(Shop.Address).%DeleteExtent()
	kill ^Shop.AccountI
	kill ^Shop.AddressI
	return $$$OK
}

Method TestUniquePhone()
{
 // retailings
 set retailing1 = ##class(Shop.Account).%New("Bohdan", "Kalika", "+380630422776", "bogdan.kalika@gmail.com")
 set regularCustomer1 = ##class(Shop.Retailing).%New("Retailing", "Bohdan Kalika, regular client", retailing1, 1)
 
 set retailing2 = ##class(Shop.Account).%New("Iryna", "Mikhailova", "+38077112233", "mikhailova@gmail.com")
 set regularCustomer2 = ##class(Shop.Retailing).%New(" ", "Iryna Mikhailova, new customer", retailing2, 0)
 

 // accounts
 set account11 = ##class(Shop.Account).%New("Tom", "Hanks", "+15446797899", "thanks@gmail.com")
 set account12 = ##class(Shop.Account).%New("Emma", "Watson", "+1347994648", "ewatson@gmail.com")
 set accounts1 = ##class(%Library.ArrayOfObjects).%New()
 do accounts1.SetAt("Tom", account11)
 do accounts1.SetAt("Tom2", account12)
 
 set address1 = ##class(Shop.Address).%New("0432", "Ukraine", "Lviv", "Donetsky", "13c")
 set wholesalingCustomer1 = ##class(Shop.Wholesaling).%New("Wholesaling", "PUMB", "Important client", address1, accounts1)
 


 set account21 = ##class(Shop.Account).%New("Igor", "Sikorsky", "+380443115799", "isikorsky@gmail.com")
 set account22 = ##class(Shop.Account).%New("Olga", "Saint", "102", "queen@gmail.com")
 set accounts2 = ##class(%Library.ArrayOfObjects).%New()
 do accounts2.SetAt("Igor", account21)
 do accounts2.SetAt("Igor2", account22)
 
 set address2 = ##class(Shop.Address).%New("03316", "Ukraine", "Kyiv", "Svitoshyn", "11")
 set wholesalingCustomer2 = ##class(Shop.Wholesaling).%New(" ", "KPI", "Usual client", address2, accounts2)
 
 do $$$AssertStatusOK(wholesalingCustomer1, "Name should be unique")
}

Method TestAccountCreation()
{
	set account = ##class(Shop.Address).%New("03316", "Ukraine", "Kyiv", "Svitoshyn", "11")
	do $$$AssertTrue($isobject(account), "Create a new account")
	set status = account.%Save()
	do $$$AssertStatusOK(status, "Account saved")
	
	set account = ##class(Shop.Address).%New("4056", "Poland", "Warshaw", "Bolesława Prusa", "11")
	do $$$AssertTrue($isobject(account), "Create a new account")
	set status = account.%Save()
	do $$$AssertStatusOK(status, "Phone is required")
}

Method TestDeletingObject()
{
 set retailing1 = ##class(Shop.Account).%New("Bohdan", "Kalika", "+380630422776", "bogdan.kalika@gmail.com")
 set regularCustomer1 = ##class(Shop.Retailing).%New("Retailing", "Bohdan Kalika, regular client", retailing1, 1)
 set status = regularCustomer1.%Save()
 do $$$AssertStatusOK(status, "RegCust saved")
 
 set regularCustomerId = regularCustomer1.%Id()
 set status = ##class(Shop.Retailing).%DeleteId(regularCustomerId)
 do $$$AssertStatusOK(status, "RegCust deleted from DB")
}

Method TestDeletingOnToMany()
{
 set retailing1 = ##class(Shop.Account).%New("Bohdan", "Kalika", "+380630422776", "bogdan.kalika@gmail.com")
 set regularCustomer1 = ##class(Shop.Retailing).%New("Retailing", "Bohdan Kalika, regular client", retailing1, 1)	
	
 set product1 = ##class(Shop.Product).%New(1, "iPhone", "IPhone XII", 33000, $zdate("04/11/2020",3))
 set product2 = ##class(Shop.Product).%New(2, "Samsung", "Samsung Galaxy S20", 18000, $zdate("03/11/2020",3))
 set product3 = ##class(Shop.Product).%New(3, "Nokia", "Nokia 5.3", 4500, $zdate("02/11/2020",3))
 
 set products1 = ##class(%Library.ListOfObjects).%New()
 do products1.Insert(product1)
 set attachment1 = ##class(%Stream.GlobalBinary).%New()
 set order1 = ##class(Shop.Order).%New(regularCustomer1, "In reserve", attachment1, products1, $zdate("25/10/2020",3))
 	
 	do $$$AssertStatusOK(order1, "Order created")
}

}
