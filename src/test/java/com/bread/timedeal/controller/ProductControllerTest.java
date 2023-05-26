package com.bread.timedeal.controller;

import static com.bread.timedeal.constants.Constants.NOW;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.bread.timedeal.domain.Product;
import com.bread.timedeal.domain.Stock;
import com.bread.timedeal.domain.TimeSale;
import com.bread.timedeal.dto.ProductCreateRequest;
import com.bread.timedeal.dto.ProductCreateResponse;
import com.bread.timedeal.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

class ProductControllerTest extends RestDocsSupport {

  private final ProductService productService = mock(ProductService.class);

  @Test
  void 상품_생성() throws Exception {

    Map<String, Object> params = new HashMap<>();
    params.put("stock", 100);
    params.put("time", "2023-05-30T23:00:00");
    params.put("name", "조던");

    given(productService.create(any(ProductCreateRequest.class)))
        .willReturn(
            ProductCreateResponse.of(new Product(1L, "조던", new Stock(100), new TimeSale(NOW)))
        );

    mockMvc.perform(
            post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(params))
        )
        .andDo(print())
        .andExpect(status().isOk())
        .andDo(document("product-create",
            preprocessRequest(prettyPrint()),
            preprocessResponse(prettyPrint()),
            requestFields(
                fieldWithPath("name").type(JsonFieldType.STRING)
                    .description("상품 명"),
                fieldWithPath("time").type(JsonFieldType.STRING)
                    .description("상품 할인 마감 날짜"),
                fieldWithPath("stock").type(JsonFieldType.NUMBER)
                    .description("상품 재고")
            ),
            responseFields(
                fieldWithPath("id").type(JsonFieldType.NUMBER)
                    .description("상품 아이디"),
                fieldWithPath("name").type(JsonFieldType.STRING)
                    .description("상품 명"),
                fieldWithPath("time").type(JsonFieldType.STRING)
                    .description("상품 할인 마감 날짜"),
                fieldWithPath("stock").type(JsonFieldType.NUMBER)
                    .description("상품 재고")
            )
        ));
  }

  @Override
  protected Object initController() {
    return new ProductController(productService);
  }
}