package com.woowacourse.ternoko.core.domain.interview.formitem;

import com.woowacourse.ternoko.core.domain.interview.Interview;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor
public class FormItems {

    @OneToMany(mappedBy = "interview", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<FormItem> formItems;

    @Override
    public int hashCode() {
        return Objects.hash(formItems);
    }

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

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final FormItems formItems1 = (FormItems) o;
        return Objects.equals(formItems, formItems1.formItems);
    }
}
