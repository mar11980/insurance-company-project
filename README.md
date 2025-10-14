# insurance-company-vaudoise
Application that allows me to create clients and create contracts for them.
As an insurance company, we sell our products to individuals and companies. As a
counselor, I want to have an application that allows me to create clients and create contracts
for them. I also want to be able to manage my clients and their contracts.
Your task as a software engineer is to create a backend system that exposes an API that will
interact with clients and contracts.

Inside the project we have four modules .

insurance-company-model
module created for entities and dtos classes 

insurance-company-api 
list all endpoint controllers to manage different services 

insurance-company-business 
to create different services using by controllers 

insurance-company-server
to boot application . This module it's the core of application.

								 ┌───────────────────────┐
								 │       Client          │
								 ├───────────────────────┤
								 │ - id: Long            │
								 │ - name: String        │
								 │ - phone: String       │
								 │ - email: String       │
								 │                       │
								 ├───────────────────────┤
								 │ + getContracts():     │
								 │     List<Contract>    │
								 └──────────┬────────────┘
											│
							 ┌──────────────┴──────────────┐
							 │                             │
			┌───────────────────────────┐      ┌────────────────────────────┐
			│       PersonClient        │      │       CompanyClient        │
			├───────────────────────────┤      ├────────────────────────────┤
			│ - birthDate: LocalDate    │      │ - companyIdentifier: String│
			└───────────────────────────┘      └────────────────────────────┘


							1 ────────────< * 
									 (owns)
				┌───────────────────────────────────────────────┐
				│                  Contract                     │
				├───────────────────────────────────────────────┤
				│ - id: Long                                    │
				│ - startDate: LocalDate                        │
				│ - endDate: LocalDate                          │
				│ - costAmount: BigDecimal                      │
				│ - updateDate: OffsetDateTime                  │
				│                                               │
				│ # client: Client                              │
				└───────────────────────────────────────────────┘