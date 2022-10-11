package com.woowacourse.ternoko.core.domain.comment.core.dto.response.core.domain.comment.core.domain.interview.formitem;

import com.woowacourse.ternoko.core.domain.interview.Interview;
import com.woowacourse.ternoko.core.domain.interview.formitem.FormItem;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor
@EqualsAndHashCode
public class FormItems {

    @OneToMany(mappedBy = "interview", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<FormItem> formItems;

    public FormItems(final List<FormItem> formItems, final Interview interview) {
        this.formItems = new ArrayList<>(formItems);
        addInterview(interview);
    }

    public void update(final List<FormItem> formItems) {
        for (int i = 0; i < formItems.size(); i++) {
            this.formItems.get(i).update(formItems.get(i));
        }
    }

    private void addInterview(final Interview interview) {
        for (FormItem formItem : formItems) {
            formItem.addInterview(interview);
        }
    }
}
