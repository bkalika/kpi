Class Shop.Retailing Extends (Shop.Client, %Persistent, %Populate)
{

Property RegularCustomer As %Boolean;

Property Account As Shop.Account;

Method %OnNew(type As Shop.ClientType, description As %String, account As Shop.Account, regularCustomer As %Boolean) As %Status
{
	set ..Type = ##class(Shop.ClientType).Retailing()
	set ..Description = description
	set ..Account = account
	set ..RegularCustomer = regularCustomer
	
	QUIT $$$OK
}

// SQL Based Queries:

Query RetailingByAccountId(account As %String) As %SQLQuery(CONTAINID = 1)
{
SELECT %ID,UniqueId,RegularCustomer,Description,Type_Name,Account_Age,Account_DateOfBirth,Account_Email,Account_FirstName,Account_LastName,Account_Phone FROM Retailing
 WHERE (RegularCustomer = :account)
 ORDER BY %ID, UniqueId
}

Storage CacheStorage
{
<Data name="Accounts">
<Attribute>Accounts</Attribute>
<Structure>subnode</Structure>
<Subscript>"Shop.Retailing.Accounts"</Subscript>
</Data>
<Data name="RetailingDefaultData">
<Subscript>"Retailing"</Subscript>
<Value name="1">
<Value>RegularCustomer</Value>
</Value>
<Value name="2">
<Value>Account</Value>
</Value>
<Value name="3">
<Value>Bonus</Value>
</Value>
</Data>
<DefaultData>RetailingDefaultData</DefaultData>
<Type>%Storage.Persistent</Type>
}

Storage Default
{
<Data name="RetailingDefaultData">
<Subscript>"Retailing"</Subscript>
<Value name="1">
<Value>RegularCustomer</Value>
</Value>
<Value name="2">
<Value>Account</Value>
</Value>
</Data>
<DefaultData>RetailingDefaultData</DefaultData>
<Type>%Library.CacheStorage</Type>
}

}
