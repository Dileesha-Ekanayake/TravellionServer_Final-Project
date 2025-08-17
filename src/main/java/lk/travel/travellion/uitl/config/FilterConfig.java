package lk.travel.travellion.uitl.config;

import lk.dileesha.jpafilter.SpecificationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.Map;

/**
 * Configuration class for setting up filters and query specifications
 * using the SpecificationBuilder. Provides predefined settings for
 * date-based, nested, and numeric field filtering to accommodate dynamic
 * query generation according to specific business requirements.
 */
@Configuration
public class FilterConfig {

    /**
     * Builds a SpecificationBuilder bean with predefined filters and configurations
     * for handling queries based on various fields and criteria such as date fields,
     * nested fields, suffixes, and numeric fields. The configuration is designed to
     * align with specific client requirements, allowing dynamic query generation.
     * <p>
     * Filters include:
     * - Date fields for handling date-based queries.
     * - Nested fields mapping flat fields to nested object properties.
     * - Date suffixes for facilitating range-based filters such as start and end dates.
     * - Numeric fields for filtering numeric values.
     *
     * @return an instance of SpecificationBuilder configured with the specified filters and mappings.
     */
    // Add your all filters here : Currently not having the all filters, added filters based on the client requirement.
    @Bean
    public SpecificationBuilder userSpecificationBuilder() {
        return SpecificationBuilder.builder()
                .withDateField("dobirth", "dobirth")
                .withDateField("createdon", "createdon")
                .withDateField("updatedon", "updatedon")
                .withDateField("validfrom", "validfrom")
                .withDateField("validto", "validto")
                .withDateField("salsefrom", "salsefrom")
                .withDateField("salseto", "salseto")

                .withDateSuffixes(Map.of("-startDate", true, "-endDate", false))

                .withNestedField("genderid", "gender.id")
                .withNestedField("designationid", "designation.id")
                .withNestedField("employeetypeid", "employeetype.id")
                .withNestedField("employeestatusid", "employeestatus.id")

                .withNestedField("userid", "user.id")

                .withNestedField("supplierid", "supplier.id")

                .withNestedField("userrolesid", "userroles.role.id")

                .withNestedField("currencyid", "currency.id")
                .withNestedField("starratingid", "starrating.id")
                .withNestedField("residenttypeid", "residenttype.id")
                .withNestedField("accommodationtypeid", "accommodationtype.id")
                .withNestedField("accommodationstatusid", "accommodationstatus.id")
                .withNestedField("accommodationroomsid", "accommodationrooms.roomtype.id")
                .withNestedField("accommodationdiscountsid", "accommodationdiscounts.accommodationdiscounttype.id")
                .withNestedField("accommodationcncelationchargesid", "accommodationcncelationcharges.cancellationscheme.id")

                .withNestedField("generictypeid", "generictype.id")
                .withNestedField("genericstatusid", "genericstatus.id")
                .withNestedField("genericratesid", "genericrates.paxtype.id")
                .withNestedField("genericdiscountsid", "genericdiscounts.genericdiscounttype.id")
                .withNestedField("genericcancellationchargesid", "genericcancellationcharges.cancellationscheme.id")

                .withNestedField("transfertypeid", "transfertype.id")
                .withNestedField("transferstatusid", "transferstatus.id")
                .withNestedField("transferratesid", "transferrates.paxtype.id")
                .withNestedField("transferdiscountsid", "transferdiscounts.transferdiscounttype.id")
                .withNestedField("transfercancellationchargesid", "transfercancellationcharges.cancellationscheme.id")

                .withNumericFields(Arrays.asList("id"))
                .build();
    }
}
