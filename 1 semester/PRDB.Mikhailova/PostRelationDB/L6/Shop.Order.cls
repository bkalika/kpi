Class Shop.Order Extends (%Persistent, %Populate)
{

Property UniqueId As %String;

Property DataOrder As %TimeStamp;

Property Status As %String;

Property Attachment As %Stream.GlobalBinary;

Property TotalSum As %Decimal;

Property Products As list Of Shop.Product;

Relationship Client As Shop.Client [ Cardinality = one, Inverse = Orders ];

Method %OnNew(client As Shop.Client, status As %String, attachment As %Stream.GlobalBinary, listOfProducts As %Library.ListOfObjects, dataOrder As %TimeStamp) As %Status
{
	set ..UniqueId = $System.Util.CreateGUID()
	set ..Client = client
	set ..DataOrder = dataOrder
	set ..Status = status
	set ..Products = listOfProducts
	do ..Attachment.CopyFrom(attachment)
	
	QUIT $$$OK
}

Method getClientInOrder(orderId As %Integer) As %Integer
{
	&sql(SELECT c.Description INTO :clientDescription
	FROM Shop.Order o
	JOIN Shop.Client c
	ON o.Client = c.Id
	WHERE Client = :orderId)
	If SQLCODE<0 {WRITE "SQLCODE error", SQLCODE RETURN %msg}
	ElseIf SQLCODE=100 {WRITE "Query returns no results" RETURN }
	RETURN clientDescription
}

Storage Default
{
<Data name="OrderDefaultData">
<Value name="1">
<Value>%%CLASSNAME</Value>
</Value>
<Value name="2">
<Value>UniqueId</Value>
</Value>
<Value name="3">
<Value>DataOrder</Value>
</Value>
<Value name="4">
<Value>Status</Value>
</Value>
<Value name="5">
<Value>Attachment</Value>
</Value>
<Value name="6">
<Value>TotalSum</Value>
</Value>
<Value name="7">
<Value>Products</Value>
</Value>
<Value name="8">
<Value>Client</Value>
</Value>
</Data>
<DataLocation>^Shop.OrderD</DataLocation>
<DefaultData>OrderDefaultData</DefaultData>
<IdLocation>^Shop.OrderD</IdLocation>
<IndexLocation>^Shop.OrderI</IndexLocation>
<StreamLocation>^Shop.OrderS</StreamLocation>
<Type>%Storage.Persistent</Type>
}

}
