package com.amoibeojt.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * レスポンスメッセージをJSON形式で返却する
 *
 * @author yure name
 */
@Data
@AllArgsConstructor
public class ResponseMessageDTO {

	private String message;
	private int status;
	private String token;
}