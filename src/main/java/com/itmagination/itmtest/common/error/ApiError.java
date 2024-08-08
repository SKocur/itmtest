package com.itmagination.itmtest.common.error;

import java.time.LocalDateTime;

record ApiError(
        String message,
        LocalDateTime time
) {
}
