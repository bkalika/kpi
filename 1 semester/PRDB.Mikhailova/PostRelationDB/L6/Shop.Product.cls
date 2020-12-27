Class Shop.Product Extends (%Persistent, %Populate)
{

Property Id As %Integer;

Property Name As %String;

Property Description As %String;

Property Prices As array Of %Decimal;

Method %OnNew(id As %Integer, name As %String, description As %String, initialPrice As %Decimal, creationDate As %Date) As %Status
{
	set ..Id = id
	set ..Name = name
	set ..Description = description
	Do ..%UpdatePrice(initialPrice, creationDate)
	
	QUIT $$$OK
}

Method toString()
{
	write ..Id 
	write ..Name 
	write ..Description 
	write ..Prices.GetAt($ZDATE($PIECE($HOROLOG,",",1)))
}

Method %UpdatePrice(initialPrice As %Decimal, creationDate As %Date)
{
	Do ..Prices.SetAt(initialPrice, creationDate)
}

ClassMethod CountProducts() As %Integer
{
	&sql(SELECT COUNT(*) INTO :count
	FROM Shop.Product)
	Quit count
}

Storage Default
{
<Data name="Prices">
<Attribute>Prices</Attribute>
<Structure>subnode</Structure>
<Subscript>"Prices"</Subscript>
</Data>
<Data name="ProductDefaultData">
<Value name="1">
<Value>%%CLASSNAME</Value>
</Value>
<Value name="2">
<Value>Id</Value>
</Value>
<Value name="3">
<Value>Name</Value>
</Value>
<Value name="4">
<Value>Description</Value>
</Value>
</Data>
<DataLocation>^Shop.ProductD</DataLocation>
<DefaultData>ProductDefaultData</DefaultData>
<IdLocation>^Shop.ProductD</IdLocation>
<IndexLocation>^Shop.ProductI</IndexLocation>
<StreamLocation>^Shop.ProductS</StreamLocation>
<Type>%Storage.Persistent</Type>
}

// COS Based Queries:

Query GetProducts(ProductName As %String) As %Query(ROWSPEC = "ProductName:%String:product name,ProductDesc:%String:Product description,ProductPrices:%List:product prices")
{
}

ClassMethod GetProductsExecute(ByRef qHandle As %Binary, ProductName As %String) As %Status
{
	Quit $$$OK
}

ClassMethod GetProductsClose(ByRef qHandle As %Binary) As %Status [ PlaceAfter = GetProductsExecute ]
{
	Quit $$$OK
}

ClassMethod GetProductsFetch(ByRef qHandle As %Binary, ByRef Row As %List, ByRef AtEnd As %Integer = 0) As %Status [ PlaceAfter = GetProductsExecute ]
{
	Quit $$$OK
}

}
