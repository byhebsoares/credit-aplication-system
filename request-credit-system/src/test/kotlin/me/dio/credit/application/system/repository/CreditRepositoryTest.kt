package me.dio.credit.application.system.repository
import me.dio.credit.application.system.entity.Address
import me.dio.credit.application.system.entity.Credit
import me.dio.credit.application.system.entity.Customer
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.test.context.ActiveProfiles
import java.math.BigDecimal
import java.time.LocalDate
import java.time.Month
import java.util.UUID

@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CreditRepositoryTest {
    @Autowired lateinit var creditRepository: CreditRepository
    @Autowired lateinit var testEntityManager: TestEntityManager

    private lateinit var customer: Customer
    private lateinit var credit1: Credit
    private lateinit var credit2: Credit

    @BeforeEach fun setup(){
        customer = testEntityManager.persist(buildCustomer())
        credit1 = testEntityManager.persist(buildCredit(customer = customer))
        credit2 = testEntityManager.persist(buildCredit(customer = customer))
    }
    @Test
    fun `sould find credit by credit code`(){
        //given
        val creditCode1 = java.util.UUID.fromString("aa547c0f-9a6a-451f-8c89-afddce916a29")
        val creditCode2 = java.util.UUID.fromString("49f740be-46a7-449b-84e7-ff5b7986d7ef")
        credit1.creditCode = creditCode1
        credit2.creditCode = creditCode2
        //when
        val fakeCredit1 : Credit = creditRepository.findByCreditCode(creditCode1)!!
        val fakeCredit2 : Credit = creditRepository.findByCreditCode(creditCode2)!!
        //then
        Assertions.assertThat(fakeCredit1).isNotNull
        Assertions.assertThat(fakeCredit2).isNotNull
        Assertions.assertThat(fakeCredit1).isSameAs(credit1)
        Assertions.assertThat(fakeCredit1).isSameAs(credit1)
    }


    private fun buildCredit(
        creditValue: BigDecimal = BigDecimal.valueOf(500.0),
        dayFirstInstallment: LocalDate = LocalDate.of(2023, Month.APRIL,22),
        numberOfInstallment: Int = 5,
        customer: Customer
    ): Credit = Credit(
        creditValue = creditValue,
        dayFirstInstallment = dayFirstInstallment,
        numberOfInstallments = numberOfInstallment,
        customer = customer
    )
    private fun buildCustomer(
        firstName: String = "Hebert",
        lastName: String = "Soares",
        cpf: String = "28475934625",
        email: String = "hebert@email.com",
        password: String = "12345",
        zipCode: String = "1234556",
        street: String = "Rua do Hebert",
        income: BigDecimal = BigDecimal.valueOf(1000.0)
    ) = Customer(
        firstName = firstName,
        lastName = lastName,
        cpf = cpf,
        email = email,
        password = password,
        address = Address(
            zipCode = zipCode,
            street = street
        ),
        income = income
    )



}