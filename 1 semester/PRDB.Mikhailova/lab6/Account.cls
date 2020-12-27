Class Shop.Account Extends (%SerialObject, %Persistent, %Library.Populate)
{

Property FirstName As %String;

Property LastName As %String;

Property Phone As %String [ Required ];

Index phoneIndex On Phone [ Unique ];

Property Email As %String [ Required ];

Property DateOfBirth As %Integer(POPSPEC = "##class(Shop.BuildingHelper).SomeDate()");

Property Age As %Integer [ Calculated, SqlComputeCode = { if ({DateOfBirth} = "") {set {Age} = ""}
 else {set {Age} = (+$h-{DateOfBirth})\365.25}}, SqlComputed ];

Method GetAge() As %String [ ServerOnly = 1 ]
{
	if ..Age > 55 {quit "Can not order this by credit: "_..Age_"$"}
	else {quit "Age: "_..Age_"$"}
}

Method %OnNew(firstName As %String, lastName As %String, phone As %String, email As %String) As %Status
{
	set ..FirstName = firstName
	set ..LastName = lastName
	set ..Phone = phone
	set ..Email = email
	
	QUIT $$$OK
}

Trigger TriggerSetNewAccount [ Event = INSERT/UPDATE, Foreach = row/object ]
{
	NEW id, SQLCODE, %msg, %ok, %oper
	SET id = (ID)
	SET account = ##class(Shop.Account).%OpenId(id)
	IF account.Email="" {
	 do account.Email.SetAt("do_not_have_email@gmail.com")
	 do account.%Save()
	 }
}

Storage Default
{
<Data name="AccountState">
<Value name="1">
<Value>FirstName</Value>
</Value>
<Value name="2">
<Value>LastName</Value>
</Value>
<Value name="3">
<Value>Phone</Value>
</Value>
<Value name="4">
<Value>Email</Value>
</Value>
<Value name="5">
<Value>DateOfBirth</Value>
</Value>
</Data>
<State>AccountState</State>
<StreamLocation>^Shop.Account</StreamLocation>
<Type>%Library.CacheSerialState</Type>
}

}
